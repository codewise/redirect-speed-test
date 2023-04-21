package com.codewise.gtmetrix.test;

public class InsufficientApiCreditsException extends RuntimeException {

    public InsufficientApiCreditsException(String message) {
        super(message);
    }
}
