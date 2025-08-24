package com.AccountService.AccountService.apis.Resources.InRequest;

import com.AccountService.AccountService.apis.validation.ValidUUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AccountTransferRequest {

    @NotNull(message = "fromAccountId cannot be empty")
    @NotBlank(message = "fromAccountId cannot be empty")
    @ValidUUID
    private String fromAccountId;

    @NotNull(message = "toAccountId cannot be empty")
    @NotBlank(message = "toAccountId cannot be empty")
    @ValidUUID
    private String toAccountId;

    @NotNull(message = "amount cannot be empty")
    @NotBlank(message = "amount cannot be empty")
    @Positive(message = "Transfer amount must be positive")
    private Float amount;

}
