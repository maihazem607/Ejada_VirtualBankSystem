package com.BFFService.BFFService.applications.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetail {

    String transactionId;
    String fromAccountId;
    String toAccountId;
    BigDecimal amount;
    String description;
    String timestamp;
    String deliveryStatus;


}
