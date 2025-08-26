package com.AccountService.AccountService.apis.Resources.OutResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountsListResponse {

    private List<AccountDetailResponse> accounts;

}