package applications.Services;



import apis.Resources.InRequest.TransferExecutionRequest;
import apis.Resources.InRequest.TransferInitiationRequest;
import apis.Resources.OutResponse.TransactionHistoryResponse;
import apis.Resources.OutResponse.TransferExecutionResponse;
import apis.Resources.OutResponse.TransferInitiationResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface TransactionService {
//
    public TransferInitiationResponse initiateTransfer(TransferInitiationRequest req) ;
    public TransferExecutionResponse executeTransfer(TransferExecutionRequest req);
    public List<TransactionHistoryResponse> getAccountTransactions(UUID accountId);}
