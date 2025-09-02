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
 * Entity representing a cooperative or farmer group in the system.
 */
@Entity
@Table(name = "cooperatives")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Cooperative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private String localGovernmentArea;

    @Column(nullable = true)
    private String registrationNumber;

    @Column(nullable = true)
    private Integer memberCount;

    @Column(nullable = true)
    private String contactPerson;

    @Column(nullable = true)
    private String contactPhone;

    @Column(nullable = true)
    private String contactEmail;

    @Column(nullable = true)
    private String address;

    @ManyToMany(mappedBy = "cooperatives")
    @Builder.Default
    private Set<Farmer> members = new HashSet<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}