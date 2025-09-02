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
 * Entity representing training programs and events for farmers.
 */
@Entity
@Table(name = "trainings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true, length = 2000)
    private String description;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    @Column(nullable = false)
    private String location;

    @Column(nullable = true)
    private String gpsCoordinates;

    @Column(nullable = true)
    private String organizer; // e.g., "BFPC", "Ministry of Agriculture", "NGO Partner"

    @Column(nullable = true)
    private String facilitator;

    @Column(nullable = true)
    private String contactPerson;

    @Column(nullable = true)
    private String contactPhone;

    @Column(nullable = true)
    private Integer capacity;

    @Column(nullable = false)
    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "training_crop_focus", joinColumns = @JoinColumn(name = "training_id"))
    @Builder.Default
    private Set<Farmer.CropType> cropFocus = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "training_attendees",
        joinColumns = @JoinColumn(name = "training_id"),
        inverseJoinColumns = @JoinColumn(name = "farmer_id")
    )
    @Builder.Default
    private Set<Farmer> attendees = new HashSet<>();

    @Column(nullable = true)
    private String trainingMaterials; // URL to training materials

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}