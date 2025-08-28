package applications.Services;



import apis.Resources.InRequest.TransferExecutionRequest;
import apis.Resources.InRequest.TransferInitiationRequest;
import apis.Resources.OutResponse.TransactionDetail;
import apis.Resources.OutResponse.TransferResponse;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface TransactionService {

    public TransferResponse initiateTransfer(TransferInitiationRequest req) ;
    public TransferResponse executeTransfer(TransferExecutionRequest req);
    public List<TransactionDetail> getAccountTransactions(String accountId);
}
