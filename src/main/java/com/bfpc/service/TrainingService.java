package com.bfpc.service;

import com.bfpc.dto.TrainingDto;
import com.bfpc.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

/**
 * Service interface for training operations.
 */
public interface TrainingService {

    /**
     * Get all trainings with pagination.
     *
     * @param pageable the pagination information
     * @return a page of trainings
     */
    Page<TrainingDto> getAllTrainings(Pageable pageable);

    /**
     * Get a training by ID.
     *
     * @param id the training ID
     * @return the training
     */
    TrainingDto getTrainingById(Long id);

    /**
     * Create a new training.
     *
     * @param trainingDto the training to create
     * @return the created training
     */
    TrainingDto createTraining(TrainingDto trainingDto);

    /**
     * Update a training.
     *
     * @param id the training ID
     * @param trainingDto the training to update
     * @return the updated training
     */
    TrainingDto updateTraining(Long id, TrainingDto trainingDto);

    /**
     * Delete a training.
     *
     * @param id the training ID
     */
    void deleteTraining(Long id);

    /**
     * Get trainings by crop type category.
     *
     * @param category the crop type category (should match a value in Farmer.CropType)
     * @param pageable the pagination information
     * @return a page of trainings
     */
    Page<TrainingDto> getTrainingsByCategory(String category, Pageable pageable);

    /**
     * Get trainings by date range.
     *
     * @param startDateTime the start date and time
     * @param endDateTime the end date and time
     * @param pageable the pagination information
     * @return a page of trainings
     */
    Page<TrainingDto> getTrainingsByDateRange(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

    /**
     * Get trainings by location.
     *
     * @param location the location
     * @param pageable the pagination information
     * @return a page of trainings
     */
    Page<TrainingDto> getTrainingsByLocation(String location, Pageable pageable);

    /**
     * Register a farmer for a training.
     *
     * @param trainingId the training ID
     * @param farmerId the farmer ID
     * @return the updated training
     */
    TrainingDto registerFarmerForTraining(Long trainingId, Long farmerId);

    /**
     * Cancel a farmer's registration for a training.
     *
     * @param trainingId the training ID
     * @param farmerId the farmer ID
     * @return the updated training
     */
    TrainingDto cancelTrainingRegistration(Long trainingId, Long farmerId);

    /**
     * Get registered farmers for a training.
     *
     * @param trainingId the training ID
     * @param pageable the pagination information
     * @return a page of farmers
     */
    Page<UserDto> getRegisteredFarmers(Long trainingId, Pageable pageable);

    /**
     * Get trainings for a farmer.
     *
     * @param farmerId the farmer ID
     * @param pageable the pagination information
     * @return a page of trainings
     */
    Page<TrainingDto> getFarmerTrainings(Long farmerId, Pageable pageable);
}