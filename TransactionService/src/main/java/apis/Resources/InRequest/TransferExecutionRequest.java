package apis.Resources.InRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransferExecutionRequest {

    @NotNull(message = "transactionId cannot be empty")
    @NotBlank(message = "transactionId cannot be empty")

    private String transactionId;
}

