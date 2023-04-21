package com.codewise.gtmetrix.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportType {
    // https://gtmetrix.com/api/docs/2.0/#api-credits
    // https://gtmetrix.com/api/docs/2.0/#api-test-start
    LIGHTHOUSE(1),
    LEGACY(0.7),
    LIGHTHOUSE_LEGACY(1.1),
    NONE(0.6);

    private final double creditsUse;

    public String getName() {
        return name().toLowerCase().replace("_", ",");
    }
}
