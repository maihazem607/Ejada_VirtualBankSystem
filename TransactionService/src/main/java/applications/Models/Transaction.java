package applications.Models;


import applications.Models.enums.DeliveryStatus;
import applications.Models.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 36)
    private UUID id;

    @Column(name = "from_account_id", length = 36)
    private UUID fromAccountId;

    @Column(name = "to_account_id",  length = 36)
    private UUID toAccountId;

    @Column(name = "amount",  precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status")
    private DeliveryStatus deliveryStatus;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    private String failureReason;

    public static Transaction initiated(UUID fromId, UUID toId, BigDecimal amount, String description) {
        Transaction t = new Transaction();

        t.fromAccountId = fromId;
        t.toAccountId = toId;
        t.amount = amount;
        t.description = description;
        t.status = TransactionStatus.INITIATED;
        t.deliveryStatus = DeliveryStatus.SENT; // initial example
        t.updatedAt = t.createdAt;
        return t;
    }

//    @PreUpdate
//    public void onUpdate() {
//        this.updatedAt = OffsetDateTime.now(ZoneOffset.UTC);
//    }


    @PrePersist
    void onInsert() {
        if (this.id == null) this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    public void markSuccess() {
        this.status = TransactionStatus.SUCCESS;
        this.failureReason = null;
        this.deliveryStatus = DeliveryStatus.DELIVERED;
    }

    public void markFailed(String reason) {
        this.status = TransactionStatus.FAILED;
        this.failureReason = reason;
        this.deliveryStatus = DeliveryStatus.FAILED;
    }

}