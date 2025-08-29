package apis.Resources.InRequest;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AccountTransactionsRequest {
    private String accountId;

}
