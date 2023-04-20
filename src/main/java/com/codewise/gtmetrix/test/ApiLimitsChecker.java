package com.codewise.gtmetrix.test;

import com.codewise.gtmetrix.api.GtmetrixApi;
import com.codewise.gtmetrix.configuration.Configuration;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@RequiredArgsConstructor
public class ApiLimitsChecker {

    private static final Logger log = LogManager.getLogger(ApiLimitsChecker.class);
    private final Configuration configuration;
    private final GtmetrixApi gtmetrixApi;

    public void ensureTestsCanBeRun() {
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
}
