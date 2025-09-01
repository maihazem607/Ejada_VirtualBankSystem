package com.AccountService.AccountService.applications.Services.Implementation;

import com.AccountService.AccountService.apis.Resources.InRequest.AccountCreationRequest;
import com.AccountService.AccountService.apis.Resources.InRequest.AccountTransferRequest;
import com.AccountService.AccountService.apis.Resources.OutResponse.AccountDetailResponse;
import com.AccountService.AccountService.apis.Resources.OutResponse.AccountResponse;
import com.AccountService.AccountService.apis.Resources.OutResponse.AccountTransferResponse;
import com.AccountService.AccountService.apis.Resources.OutResponse.AccountsListResponse;
import com.AccountService.AccountService.applications.Exceptions.AccountNotFoundException;
import com.AccountService.AccountService.applications.Exceptions.InvalidTransferRequestException;
import com.AccountService.AccountService.applications.Exceptions.NoAccountsFoundException;
import com.AccountService.AccountService.applications.Exceptions.UserNotFoundException;
import com.AccountService.AccountService.applications.Models.Account;
import com.AccountService.AccountService.applications.Repositories.AccountRepository;
import com.AccountService.AccountService.applications.Services.AccountService;
import com.AccountService.AccountService.applications.dto.UserProfileResponse;
import com.AccountService.AccountService.applications.enums.AccountStatus;
import com.AccountService.AccountService.applications.enums.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public AccountTransferResponse transferAmount(AccountTransferRequest request){

        String fromAccountId = request.getFromAccountId();
        String toAccountId = request.getToAccountId();

        //Check if accounts exist
        Account fromAccount = accountRepo.findById(fromAccountId).orElseThrow(() -> new AccountNotFoundException(fromAccountId));
        Account toAccount = accountRepo.findById(toAccountId).orElseThrow(() -> new AccountNotFoundException(toAccountId));

        //Check that its not the same account & the amount is valid
        if(fromAccountId.equals(toAccountId) || request.getAmount().compareTo(fromAccount.getBalance()) > 0) {
            throw new InvalidTransferRequestException();
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));
        accountRepo.save(fromAccount);
        accountRepo.save(toAccount);
        return new AccountTransferResponse("Account updated successfully.");
    }

    @Override
    public AccountResponse createAccount(AccountCreationRequest request) {

        String userId = request.getUserId();
        String url = "http://localhost:8090/users/" + userId + "/profile";

        try {
            restTemplate.getForObject(url, UserProfileResponse.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException(userId);
        }

        Account account = new Account();
        account.setUserId(userId);
        account.setAccountType(AccountType.valueOf(request.getAccountType()));
        account.setBalance(request.getInitialBalance());
        account.setStatus(AccountStatus.ACTIVE);

        accountRepo.save(account);

        return new AccountResponse(account.getId().toString(),
        account.getAccountNumber().toString(),"Account created successfully.");
    }


    @Override
    public AccountDetailResponse getAccountDetails(String accountId) {


        //Check if account exists
        Account account = accountRepo.findById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));
        return new AccountDetailResponse(account.getId().toString(),
                account.getAccountNumber().toString(),
                account.getBalance(),
                account.getAccountType().toString(),
                account.getStatus().toString());
    }


    @Override
    public AccountsListResponse listUserAccounts(String userId) {

            //Check if user exists with this ID
            String url = "http://localhost:8090/users/" + userId + "/profile";
            try {
                restTemplate.getForObject(url, UserProfileResponse.class);
            } catch (HttpClientErrorException.NotFound e) {
                throw new UserNotFoundException(userId);
            }
    
            List<Account> accounts = accountRepo.findByUserId(userId);

            //Check if the user has any accounts
            if (!accounts.isEmpty()) {
               AccountsListResponse accountResponses = new AccountsListResponse();
               accountResponses.setAccounts(accounts.stream().map(account -> new AccountDetailResponse(
                       account.getId().toString(),
                       account.getAccountNumber().toString(),
                       account.getBalance(),
                       account.getAccountType().toString(),
                       account.getStatus().toString()
               )).toList());
               return accountResponses;
            }
         throw new NoAccountsFoundException(userId);
    }
}
