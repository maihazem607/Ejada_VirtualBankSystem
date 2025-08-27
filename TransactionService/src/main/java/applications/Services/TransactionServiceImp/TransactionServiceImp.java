package applications.Services.TransactionServiceImp;


import apis.Resources.InRequest.TransferExecutionRequest;
import apis.Resources.InRequest.TransferInitiationRequest;
import apis.Resources.OutResponse.TransactionHistoryResponse;
import apis.Resources.OutResponse.TransferExecutionResponse;
import apis.Resources.OutResponse.TransferInitiationResponse;
import applications.Exceptons.InvalidUserDataException;
import applications.Models.Transaction;
import applications.Models.enums.TransactionStatus;
import applications.Repositories.TransactionRepository;
import applications.Services.AccountService;
import applications.Services.TransactionService;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Data

@RequiredArgsConstructor
@Service
public class TransactionServiceImp implements TransactionService {

    @Autowired
    private  final TransactionRepository transactions;
    @Autowired
    private  final AccountService accountService;

    @Override
    @Transactional
    public TransferInitiationResponse initiateTransfer(TransferInitiationRequest req) {

        if (req.getFromAccountId() != null && req.getFromAccountId().equals(req.getToAccountId())) {
            throw new InvalidUserDataException(HttpStatus.BAD_REQUEST, "Invalid Data",
                    "From and To accounts must be different.");
        }
        if (!accountService.exists(req.getFromAccountId()) || !accountService.exists(req.getToAccountId())) {
            throw new InvalidUserDataException(HttpStatus.BAD_REQUEST, "Invalid Data",
                    "Invalid 'from' or 'to' account ID.");
        }
        if (req.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidUserDataException(HttpStatus.BAD_REQUEST, "Invalid Data",
                    "Amount must be > 0.");
        }
        if (req.getFromAccountId().equals(req.getToAccountId()))
            throw new InvalidUserDataException(HttpStatus.BAD_REQUEST, "Invalid Data", "From and To accounts must be different.");

        // Create INITIATED transaction
        Transaction tx = Transaction.initiated(
                req.getFromAccountId(),
                req.getToAccountId(),
                req.getAmount(),
                req.getDescription()
        );
        transactions.save(tx);
        return new TransferInitiationResponse(
                tx.getId().toString(),
                tx.getStatus().name(),
                tx.getCreatedAt().toString()
        );
    }

//
//    @Override
//    @Transactional
//    public TransferExecutionResponse executeTransfer(TransferExecutionRequest transaction) {
//        Transaction tx = transactions.findById(transaction.getTransactionId())
//                .orElseThrow(() -> new InvalidUserDataException(
//                        HttpStatus.BAD_REQUEST, "Bad Request", "Invalid 'from' or 'to' account ID."));
//
//        if (tx.getStatus() != TransactionStatus.INITIATED) {
//            throw new InvalidUserDataException(
//                    HttpStatus.BAD_REQUEST, "Bad Request",
//                    "Transaction is not in INITIATED state.");
//        }
//        transactions.save(tx);
//
//        return new TransferExecutionResponse(
//                tx.getId().toString(), tx.getStatus().name(), Instant.now().toString());
//    }
//



    @Override
    @Transactional
    public TransferExecutionResponse executeTransfer(TransferExecutionRequest transaction) {
        Transaction tx = transactions.findById(transaction.getTransactionId()).orElseThrow(() ->
                new InvalidUserDataException(HttpStatus.BAD_REQUEST, "Bad Request",
                        "Invalid 'from' or 'to' account ID."));

        if (tx.getStatus() != TransactionStatus.INITIATED) {
            // idempotency: if already executed, just return current terminal state
            return new TransferExecutionResponse(  tx.getId().toString(), tx.getStatus().name(), tx.getCreatedAt().toString());
        }

        try {
            accountService.debit(tx.getFromAccountId(), tx.getAmount()); // may throw InsufficientFundsException
            accountService.credit(tx.getToAccountId(), tx.getAmount());

            tx.markSuccess();
            transactions.save(tx);

            return new TransferExecutionResponse(
                    tx.getId().toString(), tx.getStatus().name(), tx.getCreatedAt().toString()
            );

        } catch (InvalidUserDataException  e) {
            tx.markFailed(e.getMessage());
            transactions.save(tx);
            throw e; // mapped to 400 by advice
        } catch (RuntimeException e) {
            tx.markFailed("Unexpected error during execution");
            transactions.save(tx);
            throw e; // you can map to 500 if you like
        }
    }


    @Override
    public List<TransactionHistoryResponse> getAccountTransactions(UUID accountId) {
        var list = transactions.findByFromAccountIdOrToAccountIdOrderByCreatedAtDesc(accountId, accountId);
        if (list.isEmpty()) {
            throw new InvalidUserDataException(HttpStatus.NOT_FOUND, "Not Found",
                    "No transactions found for account ID " + accountId + ".");
        }
        return list.stream().map(t ->
                new TransactionHistoryResponse(
                        t.getId(),
                        t.getFromAccountId(),
                        t.getToAccountId(),
                        t.getAmount(),
                        t.getDescription(),
                        t.getCreatedAt(),
                        t.getDeliveryStatus().name()
                )
        ).toList();
    }

}
