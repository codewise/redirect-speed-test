package com.codewise.gtmetrix.test.data;

import com.codewise.gtmetrix.configuration.Browser;
import com.codewise.gtmetrix.configuration.Location;

public record TestResult(
        String testId,
        String reportId,
        String reportType,
        String url,
        Location location,
        Browser browser,
        int attemptNumber,
        String result
) {

    public ScheduledTest toScheduledTest() {
        return new ScheduledTest(testId, reportType, url, location, browser, attemptNumber);
    }

    public static TestResult fromScheduledTestWithResult(ScheduledTest scheduledTest, String reportId, String result) {
        return new TestResult(
                scheduledTest.testId(),
                reportId,
                scheduledTest.reportType(),
                scheduledTest.url(),
                scheduledTest.location(),
                scheduledTest.browser(),
                scheduledTest.attemptNumber(),
                result
        );
    }
}
