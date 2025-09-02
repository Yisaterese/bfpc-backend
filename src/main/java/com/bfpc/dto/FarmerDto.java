package com.bfpc.dto;

import com.bfpc.domain.entity.Farmer;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Data Transfer Object for Farmer entity.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FarmerDto {

    private Long id;
    
    private Long userId;
    
    private String firstName;
    
    private String lastName;
    
    private String email;
    
    private String phoneNumber;

    @NotNull(message = "Farm size is required")
    @Min(value = 0, message = "Farm size must be positive")
    private Double farmSizeInHectares;

    @NotBlank(message = "Location is required")
    @Size(max = 100, message = "Location must not exceed 100 characters")
    private String location;

    private String localGovernmentArea;

    private Double gpsLatitude;

    private Double gpsLongitude;

    private Set<String> cropTypes;

    private String soilType;

    @Min(value = 0, message = "Years of experience must be positive")
    private Integer yearsOfExperience;

    private Boolean hasIrrigation;

    private String primaryMarket;

    private Integer trainingSessionsAttended;

    private Integer successfulTransactions;

    private Double averageYieldPerHectare;

    private Boolean sponsorshipEligible;

    private Set<Long> cooperativeIds;
    
    private String preferredLanguage;
    
    private String address;
}