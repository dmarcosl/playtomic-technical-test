package com.playtomic.tests.wallet.api.exceptions;

import org.springframework.http.HttpStatus;

public class RestControllerError {

    private HttpStatus status;
    private String message;

    public RestControllerError(String message) {
        super();
        this.status = HttpStatus.BAD_REQUEST;
        this.message = message;
    }

    public RestControllerError(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
