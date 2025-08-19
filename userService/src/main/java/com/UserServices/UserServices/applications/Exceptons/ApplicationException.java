package com.UserServices.UserServices.applications.Exceptons;

import org.springframework.http.HttpStatus;

public abstract class ApplicationException extends RuntimeException {

    private final String error;
    private final HttpStatus httpStatus;

    public ApplicationException(String errorCode, HttpStatus httpStatus,String message) {
        super(message);
        this.error = errorCode;
        this.httpStatus = httpStatus;
    }

    public ApplicationException(HttpStatus httpStatus, String errorCode, String message) {
        super(message);
        this.error = errorCode;
        this.httpStatus = httpStatus;
    }

    public String getErrorCode() {
        return error;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
