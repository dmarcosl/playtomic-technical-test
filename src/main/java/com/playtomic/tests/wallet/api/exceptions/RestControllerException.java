package com.playtomic.tests.wallet.api.exceptions;

import org.springframework.http.HttpStatus;

public class RestControllerException extends Exception {

    public static String MINIMUM_AMOUNT_TO_RECHARGE = "Can't charge less than 10€.";
    public static String INVALID_AMOUNT_TO_PAY = "The amount must be greater than 0.";
    public static String NOT_ENOUGH_BALANCE = "Not enough balance.";

    private RestControllerError restControllerError;

    public RestControllerException(RestControllerError restControllerError) {
        super();
        this.restControllerError = restControllerError;
    }

    public RestControllerError getServiceError() {
        if (restControllerError != null) {
            return restControllerError;
        } else {
            return new RestControllerError(HttpStatus.INTERNAL_SERVER_ERROR, "Unidentified error");
        }
    }
}
