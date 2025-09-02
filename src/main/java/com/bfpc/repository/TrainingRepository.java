package com.bfpc.repository;

import com.bfpc.domain.entity.Farmer;
import com.bfpc.domain.entity.Training;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for training operations.
 */
@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

    /**
     * Find trainings by crop focus.
     *
     * @param cropType the crop type
     * @param pageable the pagination information
     * @return a page of trainings
     */
    @Query("SELECT t FROM Training t JOIN t.cropFocus c WHERE c = :cropType")
    Page<Training> findByCropFocus(Farmer.CropType cropType, Pageable pageable);

    /**
     * Find trainings by date range.
     *
     * @param startDateTime the start date and time
     * @param endDateTime the end date and time
     * @param pageable the pagination information
     * @return a page of trainings
     */
    Page<Training> findByStartDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

    /**
     * Find trainings by location.
     *
     * @param location the location
     * @param pageable the pagination information
     * @return a page of trainings
     */
    Page<Training> findByLocationIgnoreCase(String location, Pageable pageable);

    /**
     * Find trainings by farmer ID.
     *
     * @param farmerId the farmer ID
     * @param pageable the pagination information
     * @return a page of trainings
     */
    @Query("SELECT t FROM Training t JOIN t.attendees a WHERE a.id = :farmerId")
    Page<Training> findByAttendeeId(Long farmerId, Pageable pageable);

    /**
     * Find upcoming trainings.
     *
     * @param today the current date
     * @param pageable the pagination information
     * @return a page of trainings
     */
    Page<Training> findByDateGreaterThanEqualOrderByDateAsc(LocalDate today, Pageable pageable);

    /**
     * Find past trainings.
     *
     * @param today the current date
     * @param pageable the pagination information
     * @return a page of trainings
     */
    Page<Training> findByDateLessThanOrderByDateDesc(LocalDate today, Pageable pageable);
}