package com.AccountService.AccountService.apis.Resources.OutResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {

    private String accountId;
    private String accountNumber;
    private String message;

}
