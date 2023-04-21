package com.codewise.gtmetrix;

import com.codewise.gtmetrix.api.GtmetrixApi;
import com.codewise.gtmetrix.configuration.Configuration;
import com.codewise.gtmetrix.configuration.ConfigurationReader;
import com.codewise.gtmetrix.output.OutputHandler;
import com.codewise.gtmetrix.test.ApiLimitsChecker;
import com.codewise.gtmetrix.test.TestRunner;

public class RedirectSpeedTest {

    private static final String CONFIGURATION_FILE = "configuration.yml";

    public static void main(String[] args) {
        Configuration configuration = new ConfigurationReader().readConfiguration(CONFIGURATION_FILE);
        GtmetrixApi gtmetrixApi = new GtmetrixApi(configuration.getApiKey());
        new ApiLimitsChecker(configuration, gtmetrixApi).ensureTestsCanBeRun();
        try (OutputHandler outputHandler = OutputHandler.getHandlerForFile(configuration.getOutputFile())) {
            new TestRunner(configuration, outputHandler, gtmetrixApi).runTests();
        }
    }
}
