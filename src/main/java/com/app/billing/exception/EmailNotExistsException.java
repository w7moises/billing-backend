package com.app.billing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmailNotExistsException extends RuntimeException{
    private String message;

    public EmailNotExistsException(String message) {
       super(message);
    }
}
