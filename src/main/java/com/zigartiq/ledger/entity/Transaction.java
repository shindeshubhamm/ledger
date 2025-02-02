package com.zigartiq.ledger.entity;

import com.zigartiq.ledger.utils.Constants.TransactionType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime dateOfTransaction;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column
    private String description;

    public Transaction(User user, String name, Category category, String currency, BigDecimal amount,
            TransactionType type, OffsetDateTime dateOfTransaction,
            String description) {
        this.user = user;
        this.name = name;
        this.category = category;
        this.currency = currency;
        this.amount = amount;
        this.type = type;
        this.dateOfTransaction = dateOfTransaction;
        this.description = description;
    }
}