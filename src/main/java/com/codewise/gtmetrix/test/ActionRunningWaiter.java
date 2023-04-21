package com.codewise.gtmetrix.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.Duration;
import java.util.function.BooleanSupplier;

@Log4j2
@RequiredArgsConstructor
public class ActionRunningWaiter {

    private static final Duration DEFAULT_SLEEP_TIME = Duration.ofSeconds(10);
    private final Duration sleepTime;

    public ActionRunningWaiter() {
        this(DEFAULT_SLEEP_TIME);
    }

    public void waitUntilByRunningAction(BooleanSupplier waitEndingCondition, Runnable action) {
        while (!waitEndingCondition.getAsBoolean()) {
            log.debug("Running action");
            action.run();
            log.debug("Action run");
            if (!waitEndingCondition.getAsBoolean()) {
                waitWithoutThrowing();
            }
        }
    }

    private void waitWithoutThrowing() {
        try {
            log.debug("Going to sleep for {} seconds", sleepTime.toSeconds());
            Thread.sleep(sleepTime.toMillis());
            log.debug("Woke up");
        } catch (InterruptedException e) {
            log.warn("Error while waiting", e);
        }
    }
}
