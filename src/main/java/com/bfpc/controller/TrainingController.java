package com.bfpc.controller;

import com.bfpc.dto.TrainingDto;
import com.bfpc.dto.UserDto;
import com.bfpc.service.TrainingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * Controller for training operations.
 */
@RestController
@RequestMapping("/api/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    /**
     * Get all trainings with pagination.
     *
     * @param pageable the pagination information
     * @return a page of trainings
     */
    @GetMapping
    public ResponseEntity<Page<TrainingDto>> getAllTrainings(Pageable pageable) {
        return ResponseEntity.ok(trainingService.getAllTrainings(pageable));
    }

    /**
     * Get a training by ID.
     *
     * @param id the training ID
     * @return the training
     */
    @GetMapping("/{id}")
    public ResponseEntity<TrainingDto> getTrainingById(@PathVariable Long id) {
        return ResponseEntity.ok(trainingService.getTrainingById(id));
    }

    /**
     * Create a new training.
     *
     * @param trainingDto the training to create
     * @return the created training
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TrainingDto> createTraining(
            @Valid @RequestBody TrainingDto trainingDto
    ) {
        return ResponseEntity.ok(trainingService.createTraining(trainingDto));
    }

    /**
     * Update a training.
     *
     * @param id the training ID
     * @param trainingDto the training to update
     * @return the updated training
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TrainingDto> updateTraining(
            @PathVariable Long id,
            @Valid @RequestBody TrainingDto trainingDto
    ) {
        return ResponseEntity.ok(trainingService.updateTraining(id, trainingDto));
    }

    /**
     * Delete a training.
     *
     * @param id the training ID
     * @return no content
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id) {
        trainingService.deleteTraining(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get trainings by category.
     *
     * @param category the category
     * @param pageable the pagination information
     * @return a page of trainings
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<Page<TrainingDto>> getTrainingsByCategory(
            @PathVariable String category,
            Pageable pageable
    ) {
        return ResponseEntity.ok(trainingService.getTrainingsByCategory(category, pageable));
    }

    /**
     * Get trainings by date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable the pagination information
     * @return a page of trainings
     */
    @GetMapping("/date-range")
    public ResponseEntity<Page<TrainingDto>> getTrainingsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Pageable pageable
    ) {
        return ResponseEntity.ok(trainingService.getTrainingsByDateRange(startDate, endDate, pageable));
    }

    /**
     * Get trainings by location.
     *
     * @param location the location
     * @param pageable the pagination information
     * @return a page of trainings
     */
    @GetMapping("/location/{location}")
    public ResponseEntity<Page<TrainingDto>> getTrainingsByLocation(
            @PathVariable String location,
            Pageable pageable
    ) {
        return ResponseEntity.ok(trainingService.getTrainingsByLocation(location, pageable));
    }

    /**
     * Register a farmer for a training.
     *
     * @param trainingId the training ID
     * @param farmerId the farmer ID
     * @return the updated training
     */
    @PostMapping("/{trainingId}/register")
    @PreAuthorize("hasAnyRole('ADMIN', 'FARMER')")
    public ResponseEntity<TrainingDto> registerForTraining(
            @PathVariable Long trainingId,
            @RequestBody Long farmerId
    ) {
        return ResponseEntity.ok(trainingService.registerForTraining(trainingId, farmerId));
    }

    /**
     * Cancel a farmer's registration for a training.
     *
     * @param trainingId the training ID
     * @param farmerId the farmer ID
     * @return the updated training
     */
    @DeleteMapping("/{trainingId}/register/{farmerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FARMER')")
    public ResponseEntity<TrainingDto> cancelTrainingRegistration(
            @PathVariable Long trainingId,
            @PathVariable Long farmerId
    ) {
        return ResponseEntity.ok(trainingService.cancelTrainingRegistration(trainingId, farmerId));
    }

    /**
     * Get registered farmers for a training.
     *
     * @param trainingId the training ID
     * @param pageable the pagination information
     * @return a page of farmers
     */
    @GetMapping("/{trainingId}/registered-farmers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDto>> getRegisteredFarmers(
            @PathVariable Long trainingId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(trainingService.getRegisteredFarmers(trainingId, pageable));
    }

    /**
     * Get trainings for a farmer.
     *
     * @param farmerId the farmer ID
     * @param pageable the pagination information
     * @return a page of trainings
     */
    @GetMapping("/farmer/{farmerId}")
    @PreAuthorize("hasRole('ADMIN') or @farmerService.isFarmerOwner(#farmerId, principal.username)")
    public ResponseEntity<Page<TrainingDto>> getFarmerTrainings(
            @PathVariable Long farmerId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(trainingService.getFarmerTrainings(farmerId, pageable));
    }
}