package com.codewise.gtmetrix.api.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestAttributes {

    // https://gtmetrix.com/api/docs/2.0/#api-test-start
    String url;
    String location;
    String browser;
    String report;
    int retention = 1;
    int adblock = 0;
    int video = 0;

    public TestAttributes(String url, int locationId, int browserId, String reportType) {
        this.url = url;
        location = String.valueOf(locationId);
        browser = String.valueOf(browserId);
        report = reportType;
    }
}
