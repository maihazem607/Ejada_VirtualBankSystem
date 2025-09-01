package com.AccountService.AccountService.apis.Resources.InRequest;

import java.math.BigDecimal;

import com.AccountService.AccountService.apis.validation.ValueOfEnum;
import com.AccountService.AccountService.applications.enums.AccountType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;


@Data
public class AccountCreationRequest {

    @NotNull(message = "userId cannot be empty")
    @NotBlank(message = "userId cannot be empty")
    private String userId;

    @NotNull(message = "Invalid account type or initial balance")
    @NotBlank(message = "Invalid account type or initial balance")
    @ValueOfEnum(enumClass = AccountType.class, message = "Invalid account type or initial balance")
    private String accountType;

    @NotNull(message = "Invalid account type or initial balance")
    @PositiveOrZero(message = "Invalid account type or initial balance")
    private BigDecimal initialBalance;
}
