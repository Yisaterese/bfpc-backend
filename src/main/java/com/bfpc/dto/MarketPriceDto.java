package com.bfpc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for market price data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketPriceDto {

    private Long id;
    
    private Long marketId;
    
    private String marketName;

    @NotBlank(message = "Crop type is required")
    private String cropType;
    
    @NotBlank(message = "Unit is required")
    private String unit; // e.g., "kg", "bag", "ton"

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    private String location;

    @NotNull(message = "Price date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate priceDate;
    
    private String qualityGrade; // e.g., "A", "B", "C"
    
    private String source; // e.g., "Market Survey", "Government Report"
    
    private BigDecimal percentageChange;
    
    private Boolean isHighDemand;

    private String trend; // up, down, stable

    private String notes;
}