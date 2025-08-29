package com.AccountService.AccountService.apis.AccountController;

import com.AccountService.AccountService.apis.Resources.InRequest.AccountCreationRequest;
import com.AccountService.AccountService.apis.Resources.InRequest.AccountTransferRequest;
import com.AccountService.AccountService.apis.Resources.OutResponse.AccountDetailResponse;
import com.AccountService.AccountService.apis.Resources.OutResponse.AccountResponse;
import com.AccountService.AccountService.apis.Resources.OutResponse.AccountTransferResponse;
import com.AccountService.AccountService.apis.Resources.OutResponse.AccountsListResponse;
import com.AccountService.AccountService.applications.Services.AccountService;
import com.AccountService.AccountService.applications.producer.RequestLoggerProducer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;


    @Autowired
    private RequestLoggerProducer requestLoggerProducer;


    @PutMapping("/accounts/transfer")
    public ResponseEntity<AccountTransferResponse> transferAmount(@RequestBody @Valid AccountTransferRequest request) {
        requestLoggerProducer.log(request.toString(),"request");
        AccountTransferResponse response = accountService.transferAmount(request);
        requestLoggerProducer.log(response.toString(),"response");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/accounts")
    public ResponseEntity<AccountResponse> createAccount(@RequestBody @Valid AccountCreationRequest request) {
        requestLoggerProducer.log(request.toString(),"request");
        AccountResponse response = accountService.createAccount(request);
        requestLoggerProducer.log(response.toString(),"response");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<AccountDetailResponse> getAccountDetails(@PathVariable String accountId) {
        requestLoggerProducer.log(accountId,"request");
        AccountDetailResponse response = accountService.getAccountDetails(accountId);
        requestLoggerProducer.log(response.toString(),"response");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userId}/accounts")
    public ResponseEntity<AccountsListResponse> listUserAccounts(@PathVariable String userId) {
        requestLoggerProducer.log(userId,"request");
        AccountsListResponse response = accountService.listUserAccounts(userId);
        requestLoggerProducer.log(response.toString(),"response");
        return ResponseEntity.ok(response);
    }
}
