package com.BFFService.BFFService.applications.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailResponse {

    private String accountId;
    private String accountNumber;
    private BigDecimal balance;
    private String accountType;
    private String status;
}
