package com.codewise.gtmetrix.test;

import com.codewise.gtmetrix.api.GtmetrixApi;
import com.codewise.gtmetrix.configuration.Browser;
import com.codewise.gtmetrix.configuration.Configuration;
import com.codewise.gtmetrix.configuration.Location;
import com.codewise.gtmetrix.output.OutputHandler;
import com.codewise.gtmetrix.test.data.ScheduledTest;
import com.codewise.gtmetrix.test.data.TestResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.BooleanSupplier;

public class TestRunner {

    private static final Logger log = LogManager.getLogger(TestRunner.class);
    private static final Duration SLEEP_TIME = Duration.ofSeconds(10);
    private final Configuration configuration;
    private final OutputHandler outputHandler;
    private final GtmetrixApi gtmetrixApi;
    private final Set<ScheduledTest> scheduledTests;

    public TestRunner(Configuration configuration, OutputHandler outputHandler) {
        this.configuration = configuration;
        this.outputHandler = outputHandler;
        gtmetrixApi = new GtmetrixApi(configuration.getApiKey());
        scheduledTests = new LinkedHashSet<>();
    }

    public void runTests() {
        checkIfTestsCanBeRun();
        log.info("Starting running tests");
        doRunTests();
        waitUntil(scheduledTests::isEmpty);
        log.info("Finished running tests");
    }

    private void checkIfTestsCanBeRun() {
        double expectedApiUsage = configuration.getExpectedApiUsage();
        try {
            double availableApiCredits = gtmetrixApi.checkAvailableApiCredits();
            if (expectedApiUsage > availableApiCredits) {
                String message = "Available credits (%s) not enough to execute all tests (total credits required: %s)"
                        .formatted(availableApiCredits, expectedApiUsage);
                log.error(message);
                throw new InsufficientApiCreditsException(message);
            }
            log.info("Tests can be run (credits that will be used: {}, currently available credits: {})",
                    expectedApiUsage, availableApiCredits);
        } catch (IOException e) {
            log.error("Unable to check available api credits", e);
            throw new RuntimeException(e);
        }
    }

    private void doRunTests() {
        for (Location location : configuration.getLocations()) {
            for (Browser browser : configuration.getBrowsers()) {
                int numberOfTestsToRun = configuration.getNumberOfTestsToRunForBrowserAndLocation();
                for (int testNumber = 1; testNumber <= numberOfTestsToRun; testNumber++) {
                    runTest(location, browser, testNumber);
                }
            }
        }
    }

    private void runTest(Location location, Browser browser, int testNumber) {
        waitUntil(this::concurrentRequestsLimitIsNotExhausted);
        scheduleTest(location, browser, testNumber);
    }

    private boolean concurrentRequestsLimitIsNotExhausted() {
        return scheduledTests.size() < configuration.getNumberOfConcurrentTests();
    }

    private void waitUntil(BooleanSupplier waitEndingCondition) {
        while (!waitEndingCondition.getAsBoolean()) {
            checkAndHandleCompletedTests();
            if (!waitEndingCondition.getAsBoolean()) {
                waitWithoutThrowing();
            }
        }
    }

    private void checkAndHandleCompletedTests() {
        scheduledTests.stream()
                .map(this::getTestResult)
                .flatMap(Optional::stream)
                .peek(outputHandler::saveTestResult)
                .map(TestResult::toScheduledTest)
                .toList()
                .forEach(scheduledTests::remove);
    }

    private Optional<TestResult> getTestResult(ScheduledTest scheduledTest) {
        String testId = scheduledTest.testId();
        try {
            return gtmetrixApi.getTestResult(scheduledTest);
        } catch (IOException e) {
            log.error("Failed to check state for test: {}", testId, e);
            return Optional.empty();
        }
    }

    private void waitWithoutThrowing() {
        try {
            log.debug("Going to sleep for {} seconds", SLEEP_TIME.toSeconds());
            Thread.sleep(SLEEP_TIME.toMillis());
            log.debug("Woke up");
        } catch (InterruptedException e) {
            log.warn("Error while waiting", e);
        }
    }

    private void scheduleTest(Location location, Browser browser, int testNumber) {
        String url = configuration.getUrlToTest();
        String reportType = configuration.getReportType().getName();
        try {
            ScheduledTest scheduledTest = gtmetrixApi.scheduleTest(url, location, browser, reportType, testNumber);
            scheduledTests.add(scheduledTest);
            log.info("[{}/{}] Scheduled test: {}",
                    configuration.getNumberOfCurrentTestInTestSet(location, browser, testNumber),
                    configuration.getTotalNumberOfTestsToRun(), scheduledTest);
        } catch (IOException e) {
            log.error("Failed to schedule test for location: {}, browser: {}, attemptNumber: {}", location, browser,
                    testNumber, e);
        }
    }
}
