package com.app.studentgradetracker.Exception;

import org.springframework.http.HttpStatus;

public class DatabaseException extends ApiException{
    public DatabaseException(String message, HttpStatus status) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, cause);
    }
}
