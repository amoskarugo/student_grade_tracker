package com.app.studentgradetracker.Exception;

import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends ApiException{
    public DuplicateResourceException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
