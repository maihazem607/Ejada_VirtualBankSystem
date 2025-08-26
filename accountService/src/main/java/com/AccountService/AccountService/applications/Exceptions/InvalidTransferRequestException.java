package com.AccountService.AccountService.applications.Exceptions;


import org.springframework.http.HttpStatus;

import java.util.UUID;

public class InvalidTransferRequestException extends ApplicationException {

    public InvalidTransferRequestException(UUID fromAccountId, UUID toAccountId) {
        super("INVALID_TRANSFER_REQUEST", HttpStatus.BAD_REQUEST, "Invalid transfer request from account " + fromAccountId + " to account " + toAccountId);
    }

    public InvalidTransferRequestException() {
        super("INVALID_TRANSFER_REQUEST", HttpStatus.BAD_REQUEST, "Invalid transfer request.");
    }

}