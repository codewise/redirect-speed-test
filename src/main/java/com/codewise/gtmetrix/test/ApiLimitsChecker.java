package com.codewise.gtmetrix.test;

import com.codewise.gtmetrix.api.GtmetrixApi;
import com.codewise.gtmetrix.configuration.Configuration;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class ApiLimitsChecker {

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
