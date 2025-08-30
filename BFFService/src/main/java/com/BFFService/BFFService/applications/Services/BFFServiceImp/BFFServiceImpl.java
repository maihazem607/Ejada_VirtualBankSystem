package com.BFFService.BFFService.applications.Services.BFFServiceImp;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.BFFService.BFFService.apis.Resources.OutResponse.DashboardResponse;
import com.BFFService.BFFService.applications.Exceptons.ServiceException;
import com.BFFService.BFFService.applications.Services.BFFService;
import com.BFFService.BFFService.applications.dto.AccountDetailResponse;
import com.BFFService.BFFService.applications.dto.AccountsListResponse;
import com.BFFService.BFFService.applications.dto.ProfileResponse;
import com.BFFService.BFFService.applications.dto.AccountTransactionResponse;
import com.BFFService.BFFService.applications.dto.TransactionDetail;


@Service
public class BFFServiceImpl implements BFFService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public DashboardResponse getDashboard(String userId) {

        try {
            // Call User Service
            ResponseEntity<ProfileResponse> profileResponse = restTemplate.exchange("http://localhost:8090/users/" + userId + "/profile", HttpMethod.GET, null, ProfileResponse.class);
            if (profileResponse.getStatusCode() != HttpStatus.OK) {
                throw new ServiceException();
            }
            ProfileResponse userProfile = profileResponse.getBody();
            if (userProfile == null) {
                throw new ServiceException();
            }

            // Call Account Service
            ResponseEntity<AccountsListResponse> accountsResponse = restTemplate.exchange("http://localhost:8091/users/" + userId + "/accounts", HttpMethod.GET, null, AccountsListResponse.class);
            if (accountsResponse.getStatusCode() != HttpStatus.OK) {
                throw new ServiceException();
            }
            AccountsListResponse accounts = accountsResponse.getBody();
            if (accounts == null) {
                throw new ServiceException();
            }

            List<DashboardResponse.AccountSummary> accountSummaries = new ArrayList<>();
            if (accounts.getAccounts() != null) {
                for (AccountDetailResponse account : accounts.getAccounts()) {
                    // For each account, call Transaction Service
                    ResponseEntity<AccountTransactionResponse> transactionsResponse = restTemplate.exchange("http://localhost:8092/accounts/" + account.getAccountId() + "/transactions", HttpMethod.GET, null, AccountTransactionResponse.class);
                    if (transactionsResponse.getStatusCode() != HttpStatus.OK) {
                        throw new ServiceException();
                    }
                    AccountTransactionResponse accountTransactions = transactionsResponse.getBody();
                    
                    List<DashboardResponse.TransactionSummary> transactionSummaries = new ArrayList<>();
                    if (accountTransactions != null && accountTransactions.getTransactionDetailList() != null) {
                        for (TransactionDetail transaction : accountTransactions.getTransactionDetailList()) {
                            transactionSummaries.add(new DashboardResponse.TransactionSummary(
                                transaction.getTransactionId(),
                                transaction.getAmount(),
                                transaction.getToAccountId(),
                                transaction.getDescription(),
                                transaction.getTimestamp()
                            ));
                        }
                    }

                    accountSummaries.add(new DashboardResponse.AccountSummary(
                        account.getAccountId(),
                        account.getAccountNumber(),
                        account.getAccountType(),
                        account.getBalance(),
                        transactionSummaries.toArray(new DashboardResponse.TransactionSummary[0])
                    ));
                }
            }

            return new DashboardResponse(
                userProfile.getUserId().toString(),
                userProfile.getUsername(),
                userProfile.getEmail(),
                userProfile.getFirstName(),
                userProfile.getLastName(),
                accountSummaries.toArray(new DashboardResponse.AccountSummary[0])
            );

        } catch (Exception e) {
            throw new ServiceException();
        }
    }
}
