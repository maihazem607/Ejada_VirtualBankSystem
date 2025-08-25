package com.AccountService.AccountService.applications.Services.Implementation;

import com.AccountService.AccountService.apis.Resources.InRequest.AccountCreationRequest;
import com.AccountService.AccountService.apis.Resources.InRequest.AccountTransferRequest;
import com.AccountService.AccountService.apis.Resources.OutResponse.AccountDetailResponse;
import com.AccountService.AccountService.apis.Resources.OutResponse.AccountResponse;
import com.AccountService.AccountService.apis.Resources.OutResponse.AccountTransferResponse;
import com.AccountService.AccountService.applications.Exceptons.AccountNotFoundException;
import com.AccountService.AccountService.applications.Exceptons.InvalidTransferRequestException;
import com.AccountService.AccountService.applications.Models.Account;
import com.AccountService.AccountService.applications.Repositories.AccountRepo;
import com.AccountService.AccountService.applications.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Override
    public AccountTransferResponse transferAmount(AccountTransferRequest request){
        UUID fromAccountId = UUID.fromString(request.getFromAccountId());
        UUID toAccountId = UUID.fromString(request.getToAccountId());

        Account fromAccount = accountRepo.findById(fromAccountId).orElseThrow(AccountNotFoundException::new);
        //Account toAccount = accountRepo.findById(toAccountId).orElseThrow(AccountNotFoundException::new);

        if(fromAccountId.equals(toAccountId) || request.getAmount().compareTo(fromAccount.getBalance()) > 0) {
            throw new InvalidTransferRequestException();
        }

      // TODO: Add logic from transaction service after it's done

        return new AccountTransferResponse("Account updated successfully.");
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
