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
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing agricultural conferences that farmers can attend through sponsorship.
 */
@Entity
@Table(name = "conferences")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true, length = 2000)
    private String description;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    @Column(nullable = false)
    private String location;

    @Column(nullable = true)
    private String venue;

    @Column(nullable = true)
    private String organizer;

    @Column(nullable = true)
    private String website;

    @Column(nullable = true)
    private BigDecimal registrationFee;

    @Column(nullable = true)
    private Integer totalSponsorship; // Number of farmers to be sponsored

    @Column(nullable = true)
    private Integer availableSponsorship; // Remaining sponsorship slots

    @ManyToMany
    @JoinTable(
        name = "conference_sponsors",
        joinColumns = @JoinColumn(name = "conference_id"),
        inverseJoinColumns = @JoinColumn(name = "partner_id")
    )
    @Builder.Default
    private Set<Partner> sponsors = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "conference_attendees",
        joinColumns = @JoinColumn(name = "conference_id"),
        inverseJoinColumns = @JoinColumn(name = "farmer_id")
    )
    @Builder.Default
    private Set<Farmer> attendees = new HashSet<>();

    @Column(nullable = false)
    private Boolean isActive;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}