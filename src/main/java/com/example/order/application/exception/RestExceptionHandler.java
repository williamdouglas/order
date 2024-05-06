package com.example.order.application.exception;

public class RestExceptionHandler extends RuntimeException {

    private static final long servialVersionUID = 1L;

    public RestExceptionHandler(String message) {
        super(message);
    }

    public RestExceptionHandler(Throwable cause) {
        super(cause);
    }

    public RestExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }

}