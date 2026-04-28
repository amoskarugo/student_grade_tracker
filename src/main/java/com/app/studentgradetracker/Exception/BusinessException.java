package com.app.studentgradetracker.Exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends ApiException{
    public BusinessException(String message, HttpStatus status) {
        super(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
