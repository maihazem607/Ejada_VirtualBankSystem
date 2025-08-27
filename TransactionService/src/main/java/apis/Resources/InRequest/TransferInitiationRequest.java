package apis.Resources.InRequest;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;
@Data
public class TransferInitiationRequest {


    @NotNull(message = "fromAccountId is required")
    private UUID fromAccountId;

    @NotNull(message = "toAccountId is required")
    public UUID toAccountId;

    @NotNull(message = "Amount is required")
    @DecimalMin("0.01")
    private BigDecimal amount;


    private String description;

}


