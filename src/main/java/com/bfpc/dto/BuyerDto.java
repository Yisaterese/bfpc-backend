package com.bfpc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Data Transfer Object for Buyer entity.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuyerDto {

    private Long id;
    
    private Long userId;
    
    private String firstName;
    
    private String lastName;
    
    private String email;
    
    private String phoneNumber;

    @NotBlank(message = "Company name is required")
    @Size(max = 100, message = "Company name must not exceed 100 characters")
    private String companyName;

    @Size(max = 500, message = "Company description must not exceed 500 characters")
    private String companyDescription;

    @NotBlank(message = "Registration number is required")
    @Size(max = 50, message = "Registration number must not exceed 50 characters")
    private String registrationNumber;

    @NotBlank(message = "Tax identification number is required")
    @Size(max = 50, message = "Tax identification number must not exceed 50 characters")
    private String taxIdentificationNumber;

    private Boolean verified;

    @Size(max = 255, message = "Website must not exceed 255 characters")
    private String website;

    @Size(max = 255, message = "Business address must not exceed 255 characters")
    private String businessAddress;

    private Set<String> cropInterests;

    private Integer successfulTransactions;

    private Double averageRating;
    
    private String preferredLanguage;
    
    private String address;
}