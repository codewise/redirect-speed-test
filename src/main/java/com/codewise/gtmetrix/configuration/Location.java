package com.codewise.gtmetrix.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Location {
    // https://gtmetrix.com/locations.html the ones below are all free tier locations
    VANCOUVER_CANADA(1),
    LONDON_UK(2),
    SYDNEY_AUSTRALIA(3),
    SAN_ANTONIO_USA(4),
    MUMBAI_INDIA(5),
    SAO_PAULO_BRAZIL(6),
    HONG_KONG_CHINA(7);

    private final int locationId;
}
