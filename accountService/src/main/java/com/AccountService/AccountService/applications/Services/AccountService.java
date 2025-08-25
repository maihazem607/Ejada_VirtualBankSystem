package com.AccountService.AccountService.applications.Services;

import com.AccountService.AccountService.apis.Resources.InRequest.AccountCreationRequest;
import com.AccountService.AccountService.apis.Resources.InRequest.AccountTransferRequest;
import com.AccountService.AccountService.apis.Resources.OutResponse.AccountDetailResponse;
import com.AccountService.AccountService.apis.Resources.OutResponse.AccountResponse;
import com.AccountService.AccountService.apis.Resources.OutResponse.AccountTransferResponse;

import java.util.List;

public interface AccountService {
    AccountTransferResponse transferAmount(AccountTransferRequest request);
    AccountResponse createAccount(AccountCreationRequest request);
    AccountDetailResponse getAccountDetails(String accountId);
    List<AccountDetailResponse> listUserAccounts(String userId);
}
