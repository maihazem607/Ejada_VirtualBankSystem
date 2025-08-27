package com.AccountService.AccountService.applications.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class TransactionResponse {
    private String transactionId;
    private String fromAccountId;
    private String toAccountId;
    private BigDecimal amount;
    private String description;
    private OffsetDateTime timestamp;
    private String deliveryStatus;
}
