package com.sanka.mailservice.exception;

public class ServiceDownException extends RuntimeException {

    public ServiceDownException() {
        super("Our services are temporarily down. Please try again later");
    }

}
