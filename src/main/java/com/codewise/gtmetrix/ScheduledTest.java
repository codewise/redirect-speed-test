package com.codewise.gtmetrix;

public class ScheduledTest {
    private String testId;
    private int location;
    private int browser;
    private String url;
    private int attemptNumber;

    public ScheduledTest(String testId, int location, int browser, String url, int attemptNumber) {
        this.testId = testId;
        this.location = location;
        this.browser = browser;
        this.url = url;
        this.attemptNumber = attemptNumber;
    }

    public String getTestId() {
        return testId;
    }

    public int getLocation() {
        return location;
    }

    public int getBrowser() {
        return browser;
    }

    public String getUrl() {
        return url;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }
}
