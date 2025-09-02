package com.bfpc.service;

import com.bfpc.domain.entity.YieldData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for managing yield data.
 */
public interface YieldDataService {

    /**
     * Get all yield data with pagination.
     *
     * @param pageable the pagination information
     * @return a page of yield data
     */
    Page<YieldData> getAllYieldData(Pageable pageable);

    /**
     * Get yield data by ID.
     *
     * @param id the yield data ID
     * @return the yield data
     */
    YieldData getYieldDataById(Long id);

    /**
     * Create new yield data.
     *
     * @param yieldData the yield data to create
     * @return the created yield data
     */
    YieldData createYieldData(YieldData yieldData);

    /**
     * Update yield data.
     *
     * @param id the yield data ID
     * @param yieldData the yield data to update
     * @return the updated yield data
     */
    YieldData updateYieldData(Long id, YieldData yieldData);

    /**
     * Delete yield data.
     *
     * @param id the yield data ID
     */
    void deleteYieldData(Long id);

    /**
     * Get yield data by farmer ID.
     *
     * @param farmerId the farmer ID
     * @param pageable the pagination information
     * @return a page of yield data
     */
    Page<YieldData> getYieldDataByFarmerId(Long farmerId, Pageable pageable);

    /**
     * Get yield data by crop type.
     *
     * @param cropType the crop type
     * @param pageable the pagination information
     * @return a page of yield data
     */
    Page<YieldData> getYieldDataByCropType(String cropType, Pageable pageable);

    /**
     * Get yield data by harvest date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable the pagination information
     * @return a page of yield data
     */
    Page<YieldData> getYieldDataByHarvestDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable);

    /**
     * Get top yield data by yield per hectare.
     *
     * @param limit the number of records to return
     * @return a list of yield data
     */
    List<YieldData> getTopYieldDataByYieldPerHectare(int limit);

    /**
     * Get average yield per hectare by crop type.
     *
     * @param cropType the crop type
     * @return the average yield per hectare
     */
    Double getAverageYieldPerHectareByCropType(String cropType);

    /**
     * Get yield data by farmer and crop type.
     *
     * @param farmerId the farmer ID
     * @param cropType the crop type
     * @param pageable the pagination information
     * @return a page of yield data
     */
    Page<YieldData> getYieldDataByFarmerAndCropType(Long farmerId, String cropType, Pageable pageable);
}