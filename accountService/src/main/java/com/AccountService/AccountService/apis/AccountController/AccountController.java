package com.AccountService.AccountService.apis.AccountController;

import com.AccountService.AccountService.apis.Resources.InRequest.AccountCreationRequest;
import com.AccountService.AccountService.apis.Resources.InRequest.AccountTransferRequest;
import com.AccountService.AccountService.apis.Resources.OutResponse.AccountDetailResponse;
import com.AccountService.AccountService.apis.Resources.OutResponse.AccountResponse;
import com.AccountService.AccountService.apis.Resources.OutResponse.AccountTransferResponse;
import com.AccountService.AccountService.applications.Services.AccountService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PutMapping("/accounts/transfer")
    public ResponseEntity<AccountTransferResponse> transferAmount(@RequestBody @Valid AccountTransferRequest request) {
        AccountTransferResponse response = accountService.transferAmount(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/accounts")
    public ResponseEntity<AccountResponse> createAccount(@RequestBody @Valid AccountCreationRequest request) {
        AccountResponse response = accountService.createAccount(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<AccountDetailResponse> getAccountDetails(@PathVariable String accountId) {
        AccountDetailResponse response = accountService.getAccountDetails(accountId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userId}/accounts")
    public ResponseEntity<List<AccountDetailResponse>> listUserAccounts(@PathVariable String userId) {
        List<AccountDetailResponse> response = accountService.listUserAccounts(userId);
        return ResponseEntity.ok(response);
    }
}
