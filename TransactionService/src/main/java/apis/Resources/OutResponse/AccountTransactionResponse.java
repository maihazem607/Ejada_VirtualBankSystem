package apis.Resources.OutResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AccountTransactionResponse {

    private List<TransactionDetail> transactionDetailList;
}
