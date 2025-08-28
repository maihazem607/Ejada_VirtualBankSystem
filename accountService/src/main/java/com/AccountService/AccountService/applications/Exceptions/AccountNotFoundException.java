package com.AccountService.AccountService.applications.Exceptions;


import org.springframework.http.HttpStatus;

import java.util.UUID;

public class AccountNotFoundException extends ApplicationException {

    public AccountNotFoundException(UUID accountId) {
        super("NOT_FOUND", HttpStatus.NOT_FOUND, "Account with ID " + accountId + " not found.");
    }

    public AccountNotFoundException() {
        super("NOT_FOUND", HttpStatus.NOT_FOUND, "Account not found.");
    }

}