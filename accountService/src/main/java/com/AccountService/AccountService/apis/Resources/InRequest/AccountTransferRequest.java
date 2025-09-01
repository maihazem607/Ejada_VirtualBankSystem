package com.AccountService.AccountService.apis.Resources.InRequest;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AccountTransferRequest {

    @NotNull(message = "fromAccountId cannot be empty")
    @NotBlank(message = "fromAccountId cannot be empty")
    private String fromAccountId;

    @NotNull(message = "toAccountId cannot be empty")
    @NotBlank(message = "toAccountId cannot be empty")
    private String toAccountId;

    @NotNull(message = "amount cannot be empty")
    @Positive(message = "Invalid transfer request")
    private BigDecimal amount;

}
