package com.AccountService.AccountService.apis.Resources.InRequest;

import java.math.BigDecimal;

import com.AccountService.AccountService.apis.validation.ValidUUID;
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
    @ValidUUID
    private String userId;

    @NotNull(message = "accountType cannot be empty")
    @NotBlank(message = "accountType cannot be empty")
    @ValueOfEnum(enumClass = AccountType.class, message = "Account type must be either SAVINGS or CHECKING")
    private String accountType;

    @NotNull(message = "initialBalance cannot be empty")
    @NotBlank(message = "initialBalance cannot be empty")
    @PositiveOrZero(message = "Initial balance must be zero or positive")
    private BigDecimal initialBalance;
}
