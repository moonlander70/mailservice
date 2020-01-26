package com.sanka.mailservice.exception;

public class NoRecipientsFoundException extends RuntimeException {

    public NoRecipientsFoundException() {
        super("no recipients found in 'to', 'cc' and 'bcc' fields");
    }

}
