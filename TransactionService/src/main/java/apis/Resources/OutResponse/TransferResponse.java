package apis.Resources.OutResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponse {
    private String transactionId;
    private String status;
    private String timestamp; // ISO-8601 "Z"
}
