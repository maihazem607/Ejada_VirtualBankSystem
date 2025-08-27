package com.AccountService.AccountService.applications.scheduling;

import com.AccountService.AccountService.applications.Models.Account;
import com.AccountService.AccountService.applications.Repositories.AccountRepository;
import com.AccountService.AccountService.applications.dto.TransactionResponse;
import com.AccountService.AccountService.applications.enums.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class AccountStatusJob {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String TRANSACTION_SERVICE_URL = "http://localhost:8092/accounts/{accountId}/transactions";

    @Scheduled(cron = "0 0 */1 * * *")
    public void updateAccountStatus() {
        List<Account> activeAccounts = accountRepository.findByStatus(AccountStatus.ACTIVE);

        for (Account account : activeAccounts) {
            try {
                ResponseEntity<List<TransactionResponse>> response = restTemplate.exchange(
                        TRANSACTION_SERVICE_URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<TransactionResponse>>() {},
                        account.getId()
                );

                List<TransactionResponse> transactions = response.getBody();

                if (transactions == null || transactions.isEmpty()) {
                    // No transactions, inactivate account
                    inactivateAccount(account);
                } else {
                    Optional<TransactionResponse> latestTransaction = transactions.stream()
                            .max(Comparator.comparing(TransactionResponse::getTimestamp));

                    if (latestTransaction.isPresent() && isOlderThanOneDay(latestTransaction.get().getTimestamp())) {
                        inactivateAccount(account);
                    }
                }
            } catch (HttpClientErrorException.NotFound e) {
                // No transactions found for the account, inactivate it
                inactivateAccount(account);
            }
        }
    }

    private boolean isOlderThanOneDay(OffsetDateTime dateTime) {
        return dateTime.isBefore(OffsetDateTime.now().minusDays(1));
    }

    private void inactivateAccount(Account account) {
        account.setStatus(AccountStatus.INACTIVE);
        accountRepository.save(account);
    }
}

