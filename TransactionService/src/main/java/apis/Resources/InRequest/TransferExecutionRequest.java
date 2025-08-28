package apis.Resources.InRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import apis.validation.ValidUUID;

@Data
public class TransferExecutionRequest {

    @NotNull(message = "transactionId cannot be empty")
    @NotBlank(message = "transactionId cannot be empty")
    @ValidUUID
    private String transactionId;
}

