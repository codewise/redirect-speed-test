package com.codewise.gtmetrix.api.response;

import java.util.Map;

public record Resource(
        // https://gtmetrix.com/api/docs/2.0/#api-test-resource
        // https://gtmetrix.com/api/docs/2.0/#api-report-by-id
        String type,
        String id,
        Map<String, Object> attributes,
        Map<String, Object> links
) {

}
