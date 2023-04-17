package com.codewise.gtmetrix.api.response;

import java.util.Map;

public record ApiResponse(
        Resource data,
        Map<String, Object> meta,
        Map<String, Object> links
) {

    public boolean hasAttribute(String attribute) {
        return data.attributes().containsKey(attribute);
    }

    public <T> T getAttribute(String attribute, Class<T> expectedAttributeClass) {
        if (!hasAttribute(attribute)) {
            throw new InvalidResponseFormatException(
                    "Expecting response to contain attribute \"%s\" but it is not present!".formatted(attribute));
        }
        Object attributeValue = data.attributes().get(attribute);
        if (expectedAttributeClass.isAssignableFrom(attributeValue.getClass())) {
            return expectedAttributeClass.cast(attributeValue);
        }
        throw new InvalidResponseFormatException(
                "Expecting response attribute \"%s\" to be of type %s. Actual type: %s".formatted(attribute,
                        expectedAttributeClass, attributeValue.getClass()));
    }

    public Number getNumberAttribute(String attribute) {
        return getAttribute(attribute, Number.class);
    }

    public String getStringAttribute(String attribute) {
        return getAttribute(attribute, String.class);
    }
}
