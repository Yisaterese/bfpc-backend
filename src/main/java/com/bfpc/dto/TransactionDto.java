package com.bfpc.dto;

import com.bfpc.domain.entity.Transaction;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Data Transfer Object for Transaction entity.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    private Long id;

    @NotNull(message = "Farmer ID is required")
    private Long farmerId;

    private String farmerName;

    @NotNull(message = "Buyer ID is required")
    private Long buyerId;

    private String buyerName;

    @NotBlank(message = "Crop type is required")
    private String cropType;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity must be positive")
    private Double quantity;

    @NotBlank(message = "Unit is required")
    private String unit;

    @NotNull(message = "Price per unit is required")
    @Min(value = 0, message = "Price per unit must be positive")
    private Double pricePerUnit;

    private Double totalAmount;

    private String qualityGrade;

    private String status;

    private LocalDate completionDate;

    @Size(max = 500, message = "Delivery details must not exceed 500 characters")
    private String deliveryDetails;

    private String paymentMethod;

    @Size(max = 100, message = "Transaction reference must not exceed 100 characters")
    private String transactionReference;

    private Integer farmerRating;

    private Integer buyerRating;

    private LocalDate createdAt;

    private LocalDate updatedAt;
}