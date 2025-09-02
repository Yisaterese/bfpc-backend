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
 * Entity representing a buyer or processing company in the system.
 */
@Entity
@Table(name = "buyers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = true)
    private String companyDescription;

    @Column(nullable = true)
    private String registrationNumber;

    @Column(nullable = true)
    private String taxIdentificationNumber;

    @Column(nullable = false)
    private Boolean verified;

    @Column(nullable = true)
    private String website;

    @Column(nullable = true)
    private String businessAddress;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "buyer_crop_interests", joinColumns = @JoinColumn(name = "buyer_id"))
    @Builder.Default
    private Set<Farmer.CropType> cropInterests = new HashSet<>();

    @Column(nullable = true)
    private Integer successfulTransactions;

    @Column(nullable = true)
    private Double averageRating;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}