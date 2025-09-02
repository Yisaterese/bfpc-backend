package com.bfpc.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a role in the system for authorization purposes.
 */
@Entity
@Table(name = "roles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleName name;

    @Column(nullable = true)
    private String description;

    /**
     * Enum representing the available roles in the system.
     */
    public enum RoleName {
        ROLE_ADMIN,
        ROLE_FARMER,
        ROLE_BUYER,
        ROLE_EXTENSION_OFFICER,
        ROLE_NGO_PARTNER,
        ROLE_GOVERNMENT_PARTNER
    }
}