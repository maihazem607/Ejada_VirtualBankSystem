package apis.Controller;

import apis.Resources.InRequest.TransferExecutionRequest;
import apis.Resources.InRequest.TransferInitiationRequest;
import apis.Resources.OutResponse.TransactionHistoryResponse;
import apis.Resources.OutResponse.TransferExecutionResponse;
import apis.Resources.OutResponse.TransferInitiationResponse;
import applications.Models.Transaction;
import applications.Services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transfer/initiation")
    public ResponseEntity<TransferInitiationResponse> initiate(@Valid @RequestBody TransferInitiationRequest req) {
        TransferInitiationResponse resp = transactionService.initiateTransfer(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PostMapping("/transfer/execution")
    public ResponseEntity<TransferExecutionResponse> executeTransfer(
            @Valid @RequestBody TransferExecutionRequest req) {
        TransferExecutionResponse res = transactionService.executeTransfer(req);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public ResponseEntity<List<TransactionHistoryResponse>> history(@PathVariable UUID accountId) {
        List<TransactionHistoryResponse> list = transactionService.getAccountTransactions(accountId);
        return ResponseEntity.ok(list);
    }

}
