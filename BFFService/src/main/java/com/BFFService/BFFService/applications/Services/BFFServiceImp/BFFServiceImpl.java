package com.BFFService.BFFService.applications.Services.BFFServiceImp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.BFFService.BFFService.applications.Services.BFFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.BFFService.BFFService.apis.Resources.OutResponse.DashboardResponse;
import com.BFFService.BFFService.applications.Exceptons.ServiceException;
import com.BFFService.BFFService.applications.dto.AccountDetailResponse;
import com.BFFService.BFFService.applications.dto.AccountTransactionResponse;
import com.BFFService.BFFService.applications.dto.AccountsListResponse;
import com.BFFService.BFFService.applications.dto.ProfileResponse;
import com.BFFService.BFFService.applications.dto.TransactionDetail;

@Service
public class BFFServiceImpl implements BFFService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public DashboardResponse getDashboard(String userId) {

        // 1) Get user profile — if 404 -> user not found
        ProfileResponse userProfile = fetchUserProfileOrThrow(userId);

        // 2) Get accounts — if 404 or empty -> return dashboard with NO accounts
        List<AccountDetailResponse> userAccounts = fetchUserAccounts(userId);

        if (userAccounts.isEmpty()) {
            return new DashboardResponse(
                    userProfile.getUserId().toString(),
                    userProfile.getUsername(),
                    userProfile.getEmail(),
                    userProfile.getFirstName(),
                    userProfile.getLastName(),
                    new DashboardResponse.AccountSummary[0] // no accounts
            );
        }

        // 3) For each account, try to fetch transactions:
        //    - If 404/empty -> include account with empty transactions[]
        //    - If OK -> include transactions normally
        List<DashboardResponse.AccountSummary> accountSummaries = new ArrayList<>();

        for (AccountDetailResponse account : userAccounts) {
            List<DashboardResponse.TransactionSummary> txSummaries = fetchAccountTransactionsSafe(account.getAccountId());

            accountSummaries.add(
                    new DashboardResponse.AccountSummary(
                            account.getAccountId(),
                            account.getAccountNumber(),
                            account.getAccountType(),
                            account.getBalance(),
                            txSummaries.toArray(new DashboardResponse.TransactionSummary[0])
                    )
            );
        }

        return new DashboardResponse(
                userProfile.getUserId().toString(),
                userProfile.getUsername(),
                userProfile.getEmail(),
                userProfile.getFirstName(),
                userProfile.getLastName(),
                accountSummaries.toArray(new DashboardResponse.AccountSummary[0])
        );
    }

    // ---------- Helpers ----------

    private ProfileResponse fetchUserProfileOrThrow(String userId) {
        try {
            ResponseEntity<ProfileResponse> resp = restTemplate.exchange(
                    "http://localhost:8090/users/" + userId + "/profile",
                    HttpMethod.GET,
                    null,
                    ProfileResponse.class
            );

            if (resp.getStatusCode() == HttpStatus.OK && resp.getBody() != null) {
                return resp.getBody();
            }

            // Any non-200 here means we don't consider the user found
            throw new ServiceException("User not found", HttpStatus.NOT_FOUND.value());

        } catch (HttpClientErrorException.NotFound e) {
            throw new ServiceException("User not found", HttpStatus.NOT_FOUND.value());
        } catch (HttpStatusCodeException e) {
            // For other 4xx/5xx, bubble as generic service error
            throw new ServiceException("Failed to fetch user profile", e.getStatusCode().value());
        } catch (Exception e) {
            throw new ServiceException("Unexpected error while fetching user profile");
        }
    }

    private List<AccountDetailResponse> fetchUserAccounts(String userId) {
        try {
            ResponseEntity<AccountsListResponse> resp = restTemplate.exchange(
                    "http://localhost:8091/users/" + userId + "/accounts",
                    HttpMethod.GET,
                    null,
                    AccountsListResponse.class
            );

            if (resp.getStatusCode() == HttpStatus.OK && resp.getBody() != null) {
                AccountsListResponse body = resp.getBody();
                if (body.getAccounts() == null) return Collections.emptyList();
                return body.getAccounts();
            }

            // Non-200 => treat as "no accounts" for this use-case
            return Collections.emptyList();

        } catch (HttpClientErrorException.NotFound e) {
            // No accounts for this user
            return Collections.emptyList();
        } catch (HttpStatusCodeException e) {
            // On other API errors we consider it a service failure (not a "no accounts" case)
            throw new ServiceException("Failed to fetch user accounts", e.getStatusCode().value());
        } catch (Exception e) {
            throw new ServiceException("Unexpected error while fetching user accounts");
        }
    }

    private List<DashboardResponse.TransactionSummary> fetchAccountTransactionsSafe(String accountId) {
        try {
            ResponseEntity<AccountTransactionResponse> resp = restTemplate.exchange(
                    "http://localhost:8092/accounts/" + accountId + "/transactions",
                    HttpMethod.GET,
                    null,
                    AccountTransactionResponse.class
            );

            if (resp.getStatusCode() != HttpStatus.OK || resp.getBody() == null) {
                // Consider "no transactions" for this account
                return Collections.emptyList();
            }

            AccountTransactionResponse body = resp.getBody();
            if (body.getTransactionDetailList() == null) {
                return Collections.emptyList();
            }

            List<DashboardResponse.TransactionSummary> summaries = new ArrayList<>();
            for (TransactionDetail t : body.getTransactionDetailList()) {
                if (t == null) continue;
                summaries.add(new DashboardResponse.TransactionSummary(
                        Objects.toString(t.getTransactionId(), null),
                        t.getAmount(),
                        t.getToAccountId(),
                        t.getDescription(),
                        t.getTimestamp()
                ));
            }
            return summaries;

        } catch (HttpClientErrorException.NotFound e) {
            // 404 from Transaction Service => no transactions for this account
            return Collections.emptyList();
        } catch (HttpStatusCodeException e) {
            // Other API errors -> treat as service error (since it might be transient but critical)
            throw new ServiceException("Failed to fetch transactions for account " + accountId , e.getStatusCode().value());
        } catch (Exception e) {
            throw new ServiceException("Unexpected error while fetching transactions for account " + accountId);
        }
    }
}
