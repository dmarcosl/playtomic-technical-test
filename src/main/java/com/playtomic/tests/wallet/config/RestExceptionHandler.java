package com.playtomic.tests.wallet.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.playtomic.tests.wallet.api.exceptions.RestControllerError;
import com.playtomic.tests.wallet.api.exceptions.RestControllerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RestControllerException.class)
    public ResponseEntity<RestControllerError> handle(Exception exception, HttpServletRequest request,
                                                      HttpServletResponse response) {

        RestControllerException restControllerException = (RestControllerException) exception;
        return new ResponseEntity<>(restControllerException.getServiceError(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
