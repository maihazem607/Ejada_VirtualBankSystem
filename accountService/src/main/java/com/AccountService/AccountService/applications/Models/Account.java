package com.AccountService.AccountService.applications.Models;

import com.AccountService.AccountService.applications.enums.AccountStatus;
import com.AccountService.AccountService.applications.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID userId;

    @Column(unique = true, nullable = false, length = 20)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Column(precision = 15, scale = 2, columnDefinition = "DECIMAL(15,2) default 0.00")
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(255) default 'ACTIVE'")
    private AccountStatus status;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @PrePersist
    private void generateAccountNumber() {
        if (accountNumber == null) {
            // Use the unique UUID to generate a base for the account number
            long val = Math.abs(id.getMostSignificantBits());
            String base = String.valueOf(val);

            // Pad with random digits to ensure it's 20 digits long
            SecureRandom random = new SecureRandom();
            StringBuilder sb = new StringBuilder(20);
            sb.append(base);
            while (sb.length() < 20) {
                sb.append(random.nextInt(10));
            }

            // Trim to exactly 20 digits
            this.accountNumber = sb.substring(0, 20);
        }
    }
}
