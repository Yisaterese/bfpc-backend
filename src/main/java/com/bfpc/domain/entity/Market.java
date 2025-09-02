package com.bfpc.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Entity representing market information for crops.
 */
@Entity
@Table(name = "markets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private String location;

    @Column(nullable = true)
    private String localGovernmentArea;

    @Column(nullable = true)
    private String gpsCoordinates;

    @Column(nullable = true)
    private String marketDays;

    @Column(nullable = true)
    private String operatingHours;

    @Column(nullable = true)
    private Integer estimatedDailyTraders;

    @Column(nullable = true)
    private Boolean hasStorageFacilities;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}