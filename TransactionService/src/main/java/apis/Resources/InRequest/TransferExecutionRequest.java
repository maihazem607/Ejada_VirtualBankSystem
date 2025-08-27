package apis.Resources.InRequest;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class TransferExecutionRequest {
    @NotNull
    private UUID transactionId;
}

