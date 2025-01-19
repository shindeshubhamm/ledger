package com.zigartiq.ledger.exception;

import org.springframework.http.HttpStatus;

public class LedgerApiException extends RuntimeException {

    private final HttpStatus status;
    private final String message;

    public LedgerApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public LedgerApiException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
