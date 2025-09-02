package com.bfpc.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity representing transactions between farmers and buyers.
 */
@Entity
@Table(name = "transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farmer_id", nullable = false)
    private Farmer farmer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private Buyer buyer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Farmer.CropType cropType;

    @Column(nullable = false)
    private BigDecimal quantity;

    @Column(nullable = false)
    private String unit; // e.g., "kg", "bag", "ton"

    @Column(nullable = false)
    private BigDecimal pricePerUnit;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Column(nullable = true)
    private String qualityGrade; // e.g., "A", "B", "C"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(nullable = true)
    private LocalDateTime completedAt;

    @Column(nullable = true)
    private String deliveryAddress;

    @Column(nullable = true)
    private LocalDateTime deliveryDate;

    @Column(nullable = true)
    private String paymentMethod;

    @Column(nullable = true)
    private String transactionReference;

    @Column(nullable = true)
    private Integer farmerRating; // 1-5 rating given by buyer

    @Column(nullable = true)
    private Integer buyerRating; // 1-5 rating given by farmer

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Enum representing the status of a transaction.
     */
    public enum TransactionStatus {
        INITIATED,
        NEGOTIATING,
        AGREED,
        PAYMENT_PENDING,
        PAYMENT_COMPLETED,
        DELIVERY_PENDING,
        DELIVERY_COMPLETED,
        COMPLETED,
        CANCELLED,
        DISPUTED
    }
}