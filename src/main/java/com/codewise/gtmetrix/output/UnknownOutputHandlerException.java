package com.codewise.gtmetrix.output;

public class UnknownOutputHandlerException extends RuntimeException {

    public UnknownOutputHandlerException(String fileExtension) {
        super("No output handler known for file extension \"%s\"".formatted(fileExtension));
    }
}
