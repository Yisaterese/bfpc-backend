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
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a farmer in the system.
 * Contains farmer-specific attributes and relationships.
 */
@Entity
@Table(name = "farmers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Farmer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = true)
    private Double farmSizeInHectares;

    @Column(nullable = true)
    private String farmLocation;

    @Column(nullable = true)
    private String farmGpsCoordinates;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "farmer_crop_types", joinColumns = @JoinColumn(name = "farmer_id"))
    @Builder.Default
    private Set<CropType> cropTypes = new HashSet<>();

    @Column(nullable = true)
    private String soilType;

    @Column(nullable = true)
    private Integer yearsOfExperience;

    @Column(nullable = true)
    private Boolean hasIrrigation;

    @Column(nullable = true)
    private String primaryMarket;

    @Column(nullable = true)
    private Integer trainingSessionsAttended;

    @Column(nullable = true)
    private Integer successfulTransactions;

    @Column(nullable = true)
    private Double averageYieldPerHectare;

    @Column(nullable = true)
    private Boolean eligibleForSponsorship;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "farmer_cooperatives",
        joinColumns = @JoinColumn(name = "farmer_id"),
        inverseJoinColumns = @JoinColumn(name = "cooperative_id")
    )
    @Builder.Default
    private Set<Cooperative> cooperatives = new HashSet<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Enum representing the types of crops that farmers can grow.
     */
    public enum CropType {
        MAIZE,
        RICE,
        CASSAVA,
        YAM,
        SORGHUM,
        MILLET,
        GROUNDNUT,
        SOYBEAN,
        COWPEA,
        SESAME,
        VEGETABLES,
        FRUITS,
        OTHER
    }
}