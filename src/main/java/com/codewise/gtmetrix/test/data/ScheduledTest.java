package com.codewise.gtmetrix.test.data;

import com.codewise.gtmetrix.configuration.Browser;
import com.codewise.gtmetrix.configuration.Location;

public record ScheduledTest(
        String testId,
        String reportType,
        String url,
        Location location,
        Browser browser,
        int attemptNumber) {

}
