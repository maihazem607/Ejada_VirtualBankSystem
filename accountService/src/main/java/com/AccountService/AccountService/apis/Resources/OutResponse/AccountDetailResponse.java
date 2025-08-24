package com.AccountService.AccountService.apis.Resources.OutResponse;

import com.AccountService.AccountService.applications.enums.AccountStatus;
import com.AccountService.AccountService.applications.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailResponse {

    private String accountId;
    private String accountNumber;
    private Float balance;
    private AccountType accountType;
    private AccountStatus status;
}
