package apis.Controller;

import apis.Resources.InRequest.TransferExecutionRequest;
import apis.Resources.InRequest.TransferInitiationRequest;
import apis.Resources.OutResponse.TransactionDetail;
import apis.Resources.OutResponse.TransferResponse;
import applications.Services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transactions/transfer/initiation")
    public ResponseEntity<TransferResponse> initiate(@Valid @RequestBody TransferInitiationRequest req) {
        TransferResponse resp = transactionService.initiateTransfer(req);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/transactions/transfer/execution")
    public ResponseEntity<TransferResponse> executeTransfer(@Valid @RequestBody TransferExecutionRequest req) {
        TransferResponse res = transactionService.executeTransfer(req);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public ResponseEntity<List<TransactionDetail>> history(@PathVariable String accountId) {
        List<TransactionDetail> list = transactionService.getAccountTransactions(accountId);
        return ResponseEntity.ok(list);
    }

}
