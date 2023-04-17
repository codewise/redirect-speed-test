package com.codewise.gtmetrix.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Browser {
    // https://gtmetrix.com/api/docs/2.0/#api-browsers
    FIREFOX_DESKTOP(1),
    CHROME_DESKTOP(3);

    private final int browserCode;
}
