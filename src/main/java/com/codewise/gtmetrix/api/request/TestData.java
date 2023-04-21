package com.codewise.gtmetrix.api.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestData {

    String type = "test";
    TestAttributes attributes;

    public TestData(String url, int locationId, int browserId, String reportType) {
        attributes = new TestAttributes(url, locationId, browserId, reportType);
    }
}
