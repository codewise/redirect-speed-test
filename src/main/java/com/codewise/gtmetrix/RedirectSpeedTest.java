package com.codewise.gtmetrix;

import com.csvreader.CsvWriter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class RedirectSpeedTest {

    // Put your values here
    private static final String EMAIL = "email";
    private static final String API_KEY = " apiKey";
    private static final String URL = "https://url_to_test";
    private static final String LANDING_PAGE_DOMAIN = "google";

    private static final int ATTEMPTS_PER_LOCATION = 10; // Number of tries per location, for each of the locations
    private static final int MAX_CONCURRENT_TESTS = 8; // 8 for PRO account, 2 for Free
    private static final int BROWSER_CODE = 3; // 3 for Chrome, 1 for Firefox

    public static void main(String[] args) throws IOException, InterruptedException {
        Gtmetrix gtmetrix = new Gtmetrix(EMAIL, API_KEY);
        CsvWriter csvWriter = new CsvWriter("report.csv");
        csvWriter.writeRecord(new String[] {"url", "location", "attempt", "time"});

        LinkedList<ScheduledTest> scheduledTests = new LinkedList<>();
        // Check report for every location
        for (int location = 1; location <= 7; location++) {
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
                    System.out.println(String.format("Scheduled a test: %s", scheduledTests.peekLast().getTestId()));

                    // If there's currently no report to be scheduled, check if the oldest one is ready, and if it is
                    // Try to process it. If it's not ready wait a second and try again (as a simple precaution
                    // not to fire too many requests at GTmetrix
                } else {
                    try {
                        System.out.println(String.format(
                                "Can't schedule any more tests, checking if the oldest scheduled is completed...(%s)",
                                scheduledTests.peekFirst().getTestId())
                        );
                        // If there's a completed test avaiable to analyze do it, if not wait a second and try again
                        if (gtmetrix.isTestCompleted(scheduledTests.peekFirst().getTestId())) {
                            // If test is completed, poll it from the list
                            ScheduledTest currentTest = scheduledTests.pollFirst();
                            long timeUntilLander = gtmetrix.getTimeUntilLanderFromHar(currentTest.getTestId(),
                                    LANDING_PAGE_DOMAIN);
                            processedReports++;
                            System.out.println(String.format("Id: %s, location: %s, url: %s, time: %s",
                                    currentTest.getTestId(), location, URL, timeUntilLander));
                            csvWriter.writeRecord(new String[] {URL, String.valueOf(location),
                                    String.valueOf(currentTest.getAttemptNumber()), String.valueOf(timeUntilLander)});
                            csvWriter.flush();
                        } else {
                            TimeUnit.SECONDS.sleep(1);
                            System.out.println("It's not, trying again in a second...");
                        }
                    } catch (IllegalStateException e) {
                        // If there's an error thrown (502 or SSL issues) write it down simply as an 'error'
                        ScheduledTest currentTest = scheduledTests.pollFirst();
                        System.out.println(String.format("Id: %s, location: %s, url: %s, time: %s",
                                currentTest.getTestId(), location, URL, "error"));
                        csvWriter.writeRecord(new String[] {URL, String.valueOf(location),
                                String.valueOf(currentTest.getAttemptNumber()), "error"});
                        csvWriter.flush();
                    }
                }
            }
        }
    }
}

