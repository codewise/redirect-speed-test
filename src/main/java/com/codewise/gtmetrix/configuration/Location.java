package com.codewise.gtmetrix.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Location {
    // https://gtmetrix.com/locations.html the ones below are free tier locations
    VANCOUVER_CANADA(1),
    LONDON_UK(2),
    SYDNEY_AUSTRALIA(3),
    SAN_ANTONIO_USA(4),
    MUMBAI_INDIA(5),
    SAO_PAULO_BRAZIL(6),
    HONG_KONG_CHINA(7),
    // below are paid tier test locations
    QUEBEC_CANADA(8),
    SAN_FRANCISCO_USA(9),
    CHEYENNE_USA(10),
    CHICAGO_USA(11),
    DANVILLE_USA(12),
    AMSTERDAM_NETHERLANDS(13),
    PARIS_FRANCE(14),
    FRANKFURT_GERMANY(15),
    STOCKHOLM_SWEDEN(16),
    SINGAPORE(17),
    TOKYO_JAPAN(18),
    BUSAN_SOUTH_KOREA(19),
    CHENNAI_INDIA(20),
    JOHANNESBURG_SOUTH_AFRICA(21),
    DUBAI_UAE(22),
    ;

    private final int locationId;
}
