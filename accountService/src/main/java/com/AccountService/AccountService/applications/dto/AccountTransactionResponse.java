package com.AccountService.AccountService.applications.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AccountTransactionResponse {

    private List<TransactionResponse> transactionDetailList;

    public List<TransactionResponse> getTransactionDetailList() {
        return transactionDetailList;
    }
}
