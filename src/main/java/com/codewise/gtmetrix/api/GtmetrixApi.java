package com.codewise.gtmetrix.api;

import com.codewise.gtmetrix.api.request.StartTestRequest;
import com.codewise.gtmetrix.api.response.ApiResponse;
import com.codewise.gtmetrix.configuration.Browser;
import com.codewise.gtmetrix.configuration.Location;
import com.codewise.gtmetrix.test.data.ScheduledTest;
import com.codewise.gtmetrix.test.data.TestResult;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.Duration;
import java.util.Optional;

public class GtmetrixApi {

    static final String CONTENT_TYPE = "application/vnd.api+json";
    private static final Logger log = LogManager.getLogger(GtmetrixApi.class);
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(60);
    private static final String API_URL = "https://gtmetrix.com/api/2.0";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String REDIRECT_DURATION_ATTRIBUTE = "redirect_duration";
    private final JsonConverter jsonConverter = new JsonConverter();
    private final OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT)
            .writeTimeout(DEFAULT_TIMEOUT)
            .readTimeout(DEFAULT_TIMEOUT)
            .callTimeout(DEFAULT_TIMEOUT)
            .followRedirects(true) // required to follow redirect when retrieving test result
            .build();
    private final String authorization;

    public GtmetrixApi(String apiKey) {
        // https://gtmetrix.com/api/docs/2.0/#api-authentication
        authorization = Credentials.basic(apiKey, "");
    }

    public double checkAvailableApiCredits() throws IOException {
        // https://gtmetrix.com/api/docs/2.0/#api-status
        Request request = new Request.Builder()
                .get()
                .url(API_URL + "/status")
                .header(AUTHORIZATION_HEADER, authorization)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            log.debug("Get account status response: {}", response);
            ApiResponse parsedResponse = jsonConverter.toApiResponse(response);
            log.debug("Get account status parsed response: {}", parsedResponse);
            // Gson by default deserializes all numbers to Double
            return parsedResponse.getNumberAttribute("api_credits").doubleValue();
        }
    }

    public ScheduledTest scheduleTest(String url, Location location, Browser browser, String reportType, int testNumber)
            throws IOException {
        // https://gtmetrix.com/api/docs/2.0/#api-test-start
        int locationId = location.getLocationId();
        int browserCode = browser.getBrowserCode();
        StartTestRequest startTestRequest = new StartTestRequest(url, locationId, browserCode, reportType);
        RequestBody requestBody = jsonConverter.toRequestBody(startTestRequest);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(API_URL + "/tests")
                .header(AUTHORIZATION_HEADER, authorization)
                .header(CONTENT_TYPE_HEADER, CONTENT_TYPE)
                .build();
        log.debug("Scheduling test: {}", startTestRequest);
        try (Response response = httpClient.newCall(request).execute()) {
            log.debug("Schedule test response: {}", response);
            ApiResponse parsedResponse = jsonConverter.toApiResponse(response);
            log.debug("Schedule test parsed response: {}", parsedResponse);
            String testId = parsedResponse.data().id();
            return new ScheduledTest(testId, reportType, url, location, browser, testNumber);
        }
    }

    public Optional<TestResult> getTestResult(ScheduledTest scheduledTest) throws IOException {
        // https://gtmetrix.com/api/docs/2.0/#api-test-by-id
        String testId = scheduledTest.testId();
        Request request = new Request.Builder()
                .get()
                .url(API_URL + "/tests/" + testId)
                .header(AUTHORIZATION_HEADER, authorization)
                .build();
        log.debug("Checking test state: {}", testId);
        try (Response response = httpClient.newCall(request).execute()) {
            log.debug("Check test state response: {}", response);
            ApiResponse parsedResponse = jsonConverter.toApiResponse(response);
            log.debug("Check test state parsed response: {}", parsedResponse);
            // state can be: queued, started, error or completed.
            // for queued and started we want to return no result to limit requests to api and continue asking for
            // results.
            // completed state will result in a redirect to a report page which will contain test results which we
            // can return
            // error state will contain information about error, which we want to return
            return tryToRetrieveTestResult(scheduledTest, parsedResponse)
                    .or(() -> tryToRetrieveTestError(scheduledTest, parsedResponse));
        }
    }

    private static Optional<TestResult> tryToRetrieveTestResult(ScheduledTest scheduledTest,
            ApiResponse parsedResponse) {
        // https://gtmetrix.com/api/docs/2.0/#api-report-by-id
        if (parsedResponse.hasAttribute(REDIRECT_DURATION_ATTRIBUTE)) {
            String reportId = parsedResponse.data().id();
            double result = parsedResponse.getNumberAttribute(REDIRECT_DURATION_ATTRIBUTE).doubleValue();
            log.info("Completed test: {}. Result: {}", scheduledTest.testId(), result);
            return Optional.of(TestResult.fromScheduledTestWithResult(scheduledTest, reportId, String.valueOf(result)));
        }
        return Optional.empty();
    }

    private static Optional<TestResult> tryToRetrieveTestError(ScheduledTest scheduledTest,
            ApiResponse parsedResponse) {
        String state = parsedResponse.getStringAttribute("state");
        if ("error".equals(state)) {
            String error = parsedResponse.getStringAttribute("error");
            log.error("Error while running test: {}. Error: {}", scheduledTest.testId(), error);
            return Optional.of(TestResult.fromScheduledTestWithResult(scheduledTest, "", "error: %s".formatted(error)));
        }
        return Optional.empty();
    }
}
