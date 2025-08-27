package apis.Resources.OutResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistoryResponse {

    UUID transactionId;
    UUID fromAccountId;
    UUID toAccountId;
    BigDecimal amount;
    String description;
    Instant timestamp;
    String deliveryStatus;


}
