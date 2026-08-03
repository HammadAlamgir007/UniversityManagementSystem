package com.test.firstproject.exception;

public class ExternalApiException extends RuntimeException {

    private final String responseCode;

    public ExternalApiException(
            String responseCode,
            String message) {

        super(message);

        this.responseCode = responseCode;
    }

    public String getResponseCode() {
        return responseCode;
    }

}