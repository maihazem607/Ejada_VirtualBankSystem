package com.AccountService.AccountService.applications.Exceptions;

import org.springframework.http.HttpStatus;

import java.util.UUID;
public class NoAccountsFoundException extends ApplicationException  {

    public NoAccountsFoundException(String userId) {
        super("NOT_FOUND", HttpStatus.NOT_FOUND, "No accounts found for user ID " + userId);
    }

}

