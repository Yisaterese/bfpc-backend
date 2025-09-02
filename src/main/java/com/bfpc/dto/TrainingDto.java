package com.bfpc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * DTO for training data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingDto {

    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Start date and time is required")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDateTime;

    @NotNull(message = "End date and time is required")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDateTime;

    @NotBlank(message = "Location is required")
    private String location;
    
    private String gpsCoordinates;
    
    private String organizer; // e.g., "BFPC", "Ministry of Agriculture", "NGO Partner"
    
    private String facilitator;
    
    private String contactPerson;
    
    private String contactPhone;

    @Positive(message = "Capacity must be positive")
    private Integer capacity;
    
    private Boolean isActive;

    private Set<String> cropFocus;

    private List<Long> attendees;
    
    private String trainingMaterials; // URL to training materials
}