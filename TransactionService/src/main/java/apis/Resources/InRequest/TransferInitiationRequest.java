package apis.Resources.InRequest;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransferInitiationRequest {

    @NotNull(message = "fromAccountId is required")
    @NotBlank(message = "fromAccountId is required")
    private String fromAccountId;

    @NotBlank(message = "toAccountId is required")
    @NotNull(message = "toAccountId is required")
    public String toAccountId;

    @NotNull(message = "Amount is required")
    @DecimalMin("0.01")
    private BigDecimal amount;


    private String description;

}


