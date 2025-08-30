package applications.Services.TransactionServiceImp;


import apis.Resources.InRequest.TransferExecutionRequest;
import apis.Resources.InRequest.TransferInitiationRequest;
import apis.Resources.OutResponse.TransactionDetail;
import apis.Resources.OutResponse.TransferResponse;
import applications.Exceptions.InvalidTransferRequestException;
import applications.Exceptions.NoTransactionsFound;
import applications.Exceptions.TransactionFailedException;
import applications.Exceptions.TransactionNotFoundException;
import applications.Models.Transaction;
import applications.Repositories.TransactionRepository;
import applications.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import applications.dto.AccountDetailResponse;
import applications.dto.AccountTransferRequest;
import applications.dto.AccountTransferResponse;
import applications.enums.TransactionStatus;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;




@Service
public class TransactionServiceImp implements TransactionService {

    @Autowired
    private  TransactionRepository transactions;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    @Transactional
    public TransferResponse initiateTransfer(TransferInitiationRequest req) {

        if (req.getFromAccountId() != null && req.getFromAccountId().equals(req.getToAccountId())) {
            throw new InvalidTransferRequestException();
        }

        if (req.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransferRequestException();
        }

        //Check if account exists and the from has sufficient funds

        try {
           restTemplate.getForObject("http://localhost:8091/accounts/" + req.getFromAccountId(), AccountDetailResponse.class);
           restTemplate.getForObject("http://localhost:8091/accounts/" + req.getToAccountId(), AccountDetailResponse.class);
        
        } catch (HttpClientErrorException.NotFound e) {
            throw new InvalidTransferRequestException();
        }
        


        Transaction transaction = Transaction.initiated(
                UUID.fromString(req.getFromAccountId()),
                UUID.fromString(req.getToAccountId()),
                req.getAmount(),
                req.getDescription()
        );

       transactions.save(transaction);

       return new TransferResponse(transaction.getId().toString(), transaction.getStatus().name(), transaction.getTimestamp().toInstant().toString());
    }

    @Override
    @Transactional(noRollbackFor = TransactionFailedException.class)
    public TransferResponse executeTransfer(TransferExecutionRequest req) {

        //Check Transaction ID exists
        Transaction transaction = transactions.findById(UUID.fromString(req.getTransactionId()))
                .orElseThrow(TransactionNotFoundException::new);

        //Check if Transaction is in INITIATED status
        if (transaction.getStatus() != TransactionStatus.INITIATED) {
            throw new InvalidTransferRequestException();
        }
        //Call Account Service to perform the transfer
        AccountTransferRequest accountTransferRequest = new AccountTransferRequest(
                transaction.getFromAccountId().toString(),
                transaction.getToAccountId().toString(),
                transaction.getAmount()
        );

        HttpEntity<AccountTransferRequest> requestEntity = new HttpEntity<>(accountTransferRequest);
        String url = "http://localhost:8091/accounts/transfer";

        try {
            ResponseEntity<AccountTransferResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    requestEntity,
                    AccountTransferResponse.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                transaction.markSuccess();
                transactions.save(transaction);
                return new TransferResponse(transaction.getId().toString(), transaction.getStatus().name(), transaction.getTimestamp().toInstant().toString());
            } else {
                transaction.markFailed();
                transactions.save(transaction);
                throw new TransactionFailedException();
            }
        } catch (Exception e) {
            transaction.markFailed();
            transactions.save(transaction);
            throw new TransactionFailedException();
        }
    }


    @Override
    public List<TransactionDetail> getAccountTransactions(String accountId) {
        UUID accountUuid = UUID.fromString(accountId);
        List<Transaction> accountTransactions = transactions.findByFromAccountIdOrToAccountId(accountUuid, accountUuid);

        if (accountTransactions.isEmpty()) {
            throw new NoTransactionsFound(accountId);
        }

        return accountTransactions.stream()
                .map(transaction -> {
                    BigDecimal amount = transaction.getAmount();
                    String deliveryStatus;

                    if (transaction.getStatus() == TransactionStatus.FAILED) {
                        deliveryStatus = "FAILED";
                    } else if (transaction.getFromAccountId().equals(accountUuid)) {
                        // Outgoing transaction
                        amount = amount.negate();
                        deliveryStatus = "SENT";
                    } else {
                        // Incoming transaction
                        deliveryStatus = "DELIVERED";
                    }

                    return new TransactionDetail(
                            transaction.getId().toString(),
                            transaction.getFromAccountId().toString(),
                            transaction.getToAccountId().toString(),
                            amount,
                            transaction.getDescription(),
                            transaction.getTimestamp().toInstant().toString(),
                            deliveryStatus
                    );
                })
                .toList();
    }

}
