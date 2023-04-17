package com.codewise.gtmetrix;

import com.codewise.gtmetrix.configuration.Configuration;
import com.codewise.gtmetrix.configuration.ConfigurationReader;
import com.csvreader.CsvWriter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RedirectSpeedTest {

    private static final String CONFIGURATION_FILE = "configuration.yml";

    // Put your values here
    private static final String EMAIL = "email";
    private static final String API_KEY = " apiKey";
    private static final String URL = "https://url_to_test";
    private static final String LANDING_PAGE_DOMAIN = "google";

    private static final int ATTEMPTS_PER_LOCATION = 10; // Number of tries per location, for each of the locations
    private static final int MAX_CONCURRENT_TESTS = 2; // 8 for PRO account, 2 for Free
    private static final int BROWSER_CODE = 3; // 3 for Chrome, 1 for Firefox

    private static final Map<Integer, String> LOCATIONS = Map.of(
            1, "Vancouver, Canada",
            2, "London, UK",
            3, "Sydney, Australia",
            4, "San Antonio, TX, USA",
            5, "Mumbai, India",
            6, "São Paulo, Brazil",
            7, "Hong Kong, China"
    );

    public static void main(String[] args) throws IOException, InterruptedException {
        Configuration configuration = new ConfigurationReader().readConfiguration(CONFIGURATION_FILE);
        Gtmetrix gtmetrix = new Gtmetrix(EMAIL, API_KEY);
        CsvWriter csvWriter = new CsvWriter("report.csv");
        writeToCsv(csvWriter, "url", "id", "location", "attempt", "time");

        LinkedList<ScheduledTest> scheduledTests = new LinkedList<>();
        // Check report for every location
        for (int location : LOCATIONS.keySet()) {
            int attempts = 0;
            int processedReports = 0;

            // Stop loop for location only if processed all reports from it
            while (processedReports < ATTEMPTS_PER_LOCATION) {

                // Schedule a report to be processed only if we've not reached cap on reports 'in progress' yet, and
                // if we haven't scheduled all that we want to (attemptsPerLocation)
                if (scheduledTests.size() < MAX_CONCURRENT_TESTS && attempts < ATTEMPTS_PER_LOCATION) { //
                    scheduledTests.add(new ScheduledTest(
                            gtmetrix.scheduleTest(location, 3, URL), location, BROWSER_CODE, URL, ++attempts)
                    );
                    System.out.printf("Scheduled a test: %s%n", scheduledTests.peekLast().getTestId());

                    // If there's currently no report to be scheduled, check if the oldest one is ready, and if it is
                    // Try to process it. If it's not ready wait a second and try again (as a simple precaution
                    // not to fire too many requests at GTmetrix
                } else {
                    ScheduledTest test = scheduledTests.getFirst();
                    String testId = test.getTestId();
                    try {
                        System.out.printf("Checking if the oldest scheduled test is completed...(%s)%n", testId);
                        // If there's a completed test avaiable to analyze do it, if not wait a second and try again
                        if (gtmetrix.isTestCompleted(testId)) {
                            scheduledTests.removeFirst();
                            processedReports++;
                            long timeUntilLander = gtmetrix.getTimeUntilLanderFromHar(testId, LANDING_PAGE_DOMAIN);
                            System.out.printf("Id: %s, location: %s, url: %s, time: %s%n",
                                    testId, location, URL, timeUntilLander);
                            writeToCsv(csvWriter, URL, testId,
                                    LOCATIONS.get(location), String.valueOf(test.getAttemptNumber()),
                                    String.valueOf(timeUntilLander));
                            csvWriter.flush();
                        } else {
                            System.out.println("It's not, trying again in a second...");
                            TimeUnit.SECONDS.sleep(1);
                        }
                    } catch (IllegalStateException e) {
                        scheduledTests.removeFirst();
                        processedReports++;
                        System.out.printf("Id: %s, location: %s, url: %s, time: %s%n",
                                testId, location, URL, "error: " + e);
                        writeToCsv(csvWriter, URL, testId, LOCATIONS.get(location),
                                String.valueOf(test.getAttemptNumber()), "error: " + e);
                        csvWriter.flush();
                    }
                }
            }
        }
    }

    private static void writeToCsv(CsvWriter csvWriter, String url, String testId, String location, String attempt,
            String result) throws IOException {
        csvWriter.writeRecord(new String[] {url, testId, location, attempt, result});
    }
}
