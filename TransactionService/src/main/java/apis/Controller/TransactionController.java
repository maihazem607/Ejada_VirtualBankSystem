package apis.Controller;

import apis.Resources.InRequest.AccountTransactionsRequest;
import apis.Resources.InRequest.TransferExecutionRequest;
import apis.Resources.InRequest.TransferInitiationRequest;
import apis.Resources.OutResponse.AccountTransactionResponse;
import apis.Resources.OutResponse.TransactionDetail;
import apis.Resources.OutResponse.TransferResponse;
import applications.Services.TransactionService;
import applications.producer.RequestLoggerProducer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    RequestLoggerProducer requestLoggerProducer;

    @PostMapping("/transactions/transfer/initiation")
    public ResponseEntity<TransferResponse> initiate(@Valid @RequestBody TransferInitiationRequest req) {
        requestLoggerProducer.log(req.toString(),"request");
        TransferResponse resp = transactionService.initiateTransfer(req);
        requestLoggerProducer.log(resp.toString(),"response");
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/transactions/transfer/execution")
    public ResponseEntity<TransferResponse> executeTransfer(@Valid @RequestBody TransferExecutionRequest req) {
        requestLoggerProducer.log(req.toString(),"request");
        TransferResponse res = transactionService.executeTransfer(req);
        requestLoggerProducer.log(res.toString(),"response");
        return ResponseEntity.ok(res);
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public ResponseEntity<AccountTransactionResponse> history(@PathVariable String accountId) {
        AccountTransactionsRequest accountTransactionsRequest =new AccountTransactionsRequest(accountId);
        requestLoggerProducer.log(accountTransactionsRequest.toString(),"request");
        AccountTransactionsRequest accountid= new AccountTransactionsRequest(accountTransactionsRequest.getAccountId());
        List<TransactionDetail> list = transactionService.getAccountTransactions(accountid.getAccountId());
        AccountTransactionResponse accountTransactionResponse =new AccountTransactionResponse(list);
        requestLoggerProducer.log(accountTransactionResponse.toString(),"response");
        return ResponseEntity.ok(accountTransactionResponse);
    }

}
