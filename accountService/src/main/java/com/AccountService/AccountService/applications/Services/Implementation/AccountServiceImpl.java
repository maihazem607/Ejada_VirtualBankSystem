package com.AccountService.AccountService.applications.Services.Implementation;

import com.AccountService.AccountService.apis.Resources.InRequest.AccountCreationRequest;
import com.AccountService.AccountService.apis.Resources.InRequest.AccountTransferRequest;
import com.AccountService.AccountService.apis.Resources.OutResponse.AccountDetailResponse;
import com.AccountService.AccountService.apis.Resources.OutResponse.AccountResponse;
import com.AccountService.AccountService.apis.Resources.OutResponse.AccountTransferResponse;
import com.AccountService.AccountService.applications.Services.AccountService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Override
    public AccountTransferResponse transferAmount(AccountTransferRequest request) {
        return new AccountTransferResponse();
    }

    @Override
    public AccountResponse createAccount(AccountCreationRequest request) {
        return new AccountResponse();
    }

    @Override
    public AccountDetailResponse getAccountDetails(String accountId) {
        return new AccountDetailResponse();
    }

    @Override
    public List<AccountDetailResponse> listUserAccounts(String userId) {
        return Collections.emptyList();
    }
}
