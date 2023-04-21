package com.codewise.gtmetrix.api.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StartTestRequest {

    // https://gtmetrix.com/api/docs/2.0/#api-test-start
    TestData data;

    public StartTestRequest(String url, int locationId, int browserId, String reportType) {
        data = new TestData(url, locationId, browserId, reportType);
    }
}
