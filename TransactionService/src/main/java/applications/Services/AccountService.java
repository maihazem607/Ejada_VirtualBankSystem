package applications.Services;


import java.math.BigDecimal;
import java.util.UUID;

public interface AccountService {
    void debit(UUID accountId, BigDecimal amount);
    void credit(UUID accountId, BigDecimal amount);
    boolean exists(UUID accountId);
}