package apis.Resources.OutResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferExecutionResponse {
    private String transactionId;
    private String status;
    private String timestamp; // ISO-8601 "Z"


}
