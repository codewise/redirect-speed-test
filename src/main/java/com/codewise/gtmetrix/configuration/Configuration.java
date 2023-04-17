package com.codewise.gtmetrix.configuration;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Configuration { // record will not work with snakeyaml

    String apiKey;
    String urlToTest;
    int numberOfConcurrentTests;
    ReportType reportType;
    int numberOfTestsToRunForBrowserAndLocation;
    List<Browser> browsers;
    List<Location> locations;
}
