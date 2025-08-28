package applications.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransferRequest {
    private String fromAccountId;
    private String toAccountId;
    private BigDecimal amount;
}
