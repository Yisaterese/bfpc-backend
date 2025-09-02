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
 * Entity representing partners such as NGOs, government bodies, and agribusinesses.
 */
@Entity
@Table(name = "partners")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true, length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PartnerType type;

    @Column(nullable = true)
    private String website;

    @Column(nullable = true)
    private String logoUrl;

    @Column(nullable = true)
    private String contactPerson;

    @Column(nullable = true)
    private String contactEmail;

    @Column(nullable = true)
    private String contactPhone;

    @Column(nullable = true)
    private String address;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = true)
    private String partnershipDetails;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Enum representing the types of partners.
     */
    public enum PartnerType {
        NGO,
        GOVERNMENT,
        AGRIBUSINESS,
        RESEARCH_INSTITUTION,
        FINANCIAL_INSTITUTION,
        OTHER
    }
}