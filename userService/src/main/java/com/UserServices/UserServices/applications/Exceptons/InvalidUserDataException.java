package com.UserServices.UserServices.applications.Exceptons;

import org.springframework.http.HttpStatus;

public class InvalidUserDataException extends ApplicationException {

    public InvalidUserDataException(String message, String errorCode, HttpStatus httpStatus) {
        super( httpStatus, errorCode, message);
    }

    public InvalidUserDataException() {
        super( HttpStatus.BAD_REQUEST,"Invalid Data", "INVALID_DATA");
    }
}
