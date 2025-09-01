package com.AccountService.AccountService.applications.Exceptions;


import org.springframework.http.HttpStatus;

import java.util.UUID;

public class UserNotFoundException extends ApplicationException {

    public UserNotFoundException(String userId) {
        super("USER_NOT_FOUND", HttpStatus.NOT_FOUND, "User with ID " + userId + " not found.");
    }

    public UserNotFoundException() {
        super("USER_NOT_FOUND", HttpStatus.NOT_FOUND, "User not found.");
    }

}