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
 * Entity representing yield data for farmers' crops.
 */
@Entity
@Table(name = "yield_data")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class YieldData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farmer_id", nullable = false)
    private Farmer farmer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Farmer.CropType cropType;

    @Column(nullable = false)
    private LocalDate harvestDate;

    @Column(nullable = false)
    private LocalDate plantingDate;

    @Column(nullable = false)
    private BigDecimal areaPlanted; // in hectares

    @Column(nullable = false)
    private BigDecimal yieldQuantity;

    @Column(nullable = false)
    private String yieldUnit; // e.g., "kg", "ton"

    @Column(nullable = true)
    private BigDecimal yieldPerHectare;

    @Column(nullable = true)
    private String soilType;

    @Column(nullable = true)
    private Boolean usedIrrigation;

    @Column(nullable = true)
    private Boolean usedFertilizer;

    @Column(nullable = true)
    private Boolean usedPesticides;

    @Column(nullable = true)
    private String seedVariety;

    @Column(nullable = true)
    private String weatherConditions;

    @Column(nullable = true)
    private String challenges;

    @Column(nullable = true)
    private Integer trainingImplemented; // Number of training techniques implemented

    @Column(nullable = true)
    private BigDecimal percentageChange; // Percentage change from previous yield

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}