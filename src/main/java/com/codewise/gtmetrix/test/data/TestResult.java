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

}
