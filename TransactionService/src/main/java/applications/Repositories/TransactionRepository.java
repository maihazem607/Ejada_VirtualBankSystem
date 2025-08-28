package applications.Repositories;


import applications.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByFromAccountIdOrToAccountId(UUID fromAccountId, UUID toAccountId);
}
