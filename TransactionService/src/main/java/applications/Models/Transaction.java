package applications.Models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;
import applications.enums.TransactionStatus;
import org.apache.kafka.common.serialization.Serdes;

@Data
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "from_account_id")
    private String fromAccountId;

    @Column(name = "to_account_id")
    private String toAccountId;

    @Column(precision = 15, scale = 2, columnDefinition = "DECIMAL(15,2) default 0.00", nullable = false)
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(255) default 'INITIATED'", nullable = false)
    private TransactionStatus status;

    private Timestamp timestamp;


    public static Transaction initiated(String fromId, String toId, BigDecimal amount, String description) {
        Transaction t = new Transaction();

        t.fromAccountId = fromId;
        t.toAccountId = toId;
        t.amount = amount;
        t.description = description;
        t.status = TransactionStatus.INITIATED;
        t.timestamp = Timestamp.from(Instant.now());
        return t;
    }

    @PrePersist
    void onInsert() {
        if (this.id == null) this.id = String.valueOf(UUID.randomUUID());
        this.timestamp = Timestamp.from(Instant.now());
    }

    public void markSuccess() {
        this.status = TransactionStatus.SUCCESS;
    }

    public void markFailed() {
        this.status = TransactionStatus.FAILED;
    }

}