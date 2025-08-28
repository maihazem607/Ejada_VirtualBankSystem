package com.BFFService.BFFService.apis.Resources.OutResponse;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardResponse {

    String userId;
    String username;
    String email;
    String firstName;
    String lastName;
    AccountSummary[] accounts;

    @Data
    @AllArgsConstructor
    public static class AccountSummary {
        String accountId;
        String accountNumber;
        String accountType; // e.g., DEBIT, CREDIT
        BigDecimal balance;
        TransactionSummary[] transactions;
    }

    @Data
    @AllArgsConstructor
    public static class TransactionSummary {
        String transactionId;
        BigDecimal amount;
        String toAccountId;
        String description;
        String timestamp; 
    }
}
