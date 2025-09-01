package com.UserServices.UserServices.applications.Exceptons;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class UserNotFoundException extends ApplicationException {

    public UserNotFoundException(UUID userId) {
        super("USER_NOT_FOUND", HttpStatus.NOT_FOUND, "User with ID " + userId + " not found.");
    }

    public UserNotFoundException(String userId) {
        super("USER_NOT_FOUND", HttpStatus.NOT_FOUND, "User not found.");
    }

}
