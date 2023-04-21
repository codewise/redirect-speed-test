package com.codewise.gtmetrix.configuration;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Configuration { // record will not work with snakeyaml

    String outputFile;
    String apiKey;
    String urlToTest;
    int numberOfConcurrentTests;
    ReportType reportType;
    int numberOfTestsToRunForBrowserAndLocation;
    List<Browser> browsers;
    List<Location> locations;

    @Override
    public String toString() {
        return "Configuration{" +
                "outputFile='" + outputFile + '\'' +
                ", apiKey=<HIDDEN>" +
                ", urlToTest='" + urlToTest + '\'' +
                ", numberOfConcurrentTests=" + numberOfConcurrentTests +
                ", reportType=" + reportType +
                ", numberOfTestsToRunForBrowserAndLocation=" + numberOfTestsToRunForBrowserAndLocation +
                ", browsers=" + browsers +
                ", locations=" + locations +
                '}';
    }

    public double getExpectedApiUsage() {
        return getTotalNumberOfTestsToRun() * reportType.getCreditsUse();
    }

    public int getTotalNumberOfTestsToRun() {
        return locations.size() * browsers.size() * numberOfTestsToRunForBrowserAndLocation;
    }

    public int getNumberOfCurrentTestInTestSet(Location location, Browser browser, int testNumber) {
        int testsRunForPreviousLocations =
                locations.indexOf(location) * browsers.size() * numberOfTestsToRunForBrowserAndLocation;
        int testsRunForPreviousBrowsersInCurrentLocation =
                browsers.indexOf(browser) * numberOfTestsToRunForBrowserAndLocation;
        return testsRunForPreviousLocations + testsRunForPreviousBrowsersInCurrentLocation + testNumber;
    }
}
