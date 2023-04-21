package com.codewise.gtmetrix.test;

import com.codewise.gtmetrix.api.GtmetrixApi;
import com.codewise.gtmetrix.configuration.Browser;
import com.codewise.gtmetrix.configuration.Configuration;
import com.codewise.gtmetrix.configuration.Location;
import com.codewise.gtmetrix.output.OutputHandler;
import com.codewise.gtmetrix.test.data.ScheduledTest;
import com.codewise.gtmetrix.test.data.TestResult;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class TestRunner {

    private static final Logger log = LogManager.getLogger(TestRunner.class);
    private final ActionRunningWaiter actionRunningWaiter = new ActionRunningWaiter();
    private final Set<ScheduledTest> scheduledTests = new LinkedHashSet<>();
    private final Configuration configuration;
    private final OutputHandler outputHandler;
    private final GtmetrixApi gtmetrixApi;

    public void runTests() {
        log.info("Starting running tests");
        doRunTests();
        actionRunningWaiter.waitUntilByRunningAction(scheduledTests::isEmpty, this::checkAndHandleCompletedTests);
        log.info("Finished running tests");
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
        actionRunningWaiter.waitUntilByRunningAction(this::concurrentRequestsLimitIsNotExhausted,
                this::checkAndHandleCompletedTests);
        scheduleTest(location, browser, testNumber);
    }

    private boolean concurrentRequestsLimitIsNotExhausted() {
        return scheduledTests.size() < configuration.getNumberOfConcurrentTests();
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
        try {
            return gtmetrixApi.getTestResult(scheduledTest);
        } catch (IOException e) {
            log.error("Failed to check state for test: {}", scheduledTest.testId(), e);
            return Optional.empty();
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
