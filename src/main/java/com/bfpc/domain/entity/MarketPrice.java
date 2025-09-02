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
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity representing market prices for crops.
 */
@Entity
@Table(name = "market_prices")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MarketPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id", nullable = false)
    private Market market;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Farmer.CropType cropType;

    @Column(nullable = false)
    private String unit; // e.g., "kg", "bag", "ton"

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDate priceDate;

    @Column(nullable = true)
    private String qualityGrade; // e.g., "A", "B", "C"

    @Column(nullable = true)
    private String source; // e.g., "Market Survey", "Government Report"

    @Column(nullable = true)
    private BigDecimal percentageChange;

    @Column(nullable = true)
    private Boolean isHighDemand;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}