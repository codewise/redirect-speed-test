package com.codewise.gtmetrix.test;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.function.BooleanSupplier;

@RequiredArgsConstructor
public class ActionRunningWaiter {

    private static final Logger log = LogManager.getLogger(ActionRunningWaiter.class);
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
