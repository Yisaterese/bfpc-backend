package com.bfpc.repository;

import com.bfpc.domain.entity.Farmer;
import com.bfpc.domain.entity.YieldData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository for YieldData entity.
 */
@Repository
public interface YieldDataRepository extends JpaRepository<YieldData, Long> {

    /**
     * Find yield data by farmer.
     *
     * @param farmer the farmer
     * @param pageable the pagination information
     * @return a page of yield data
     */
    Page<YieldData> findByFarmer(Farmer farmer, Pageable pageable);

    /**
     * Find yield data by farmer ID.
     *
     * @param farmerId the farmer ID
     * @param pageable the pagination information
     * @return a page of yield data
     */
    Page<YieldData> findByFarmerId(Long farmerId, Pageable pageable);

    /**
     * Find yield data by crop type.
     *
     * @param cropType the crop type
     * @param pageable the pagination information
     * @return a page of yield data
     */
    Page<YieldData> findByCropType(YieldData.CropType cropType, Pageable pageable);

    /**
     * Find yield data by harvest date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable the pagination information
     * @return a page of yield data
     */
    Page<YieldData> findByHarvestDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    /**
     * Find top yield data by yield per hectare.
     *
     * @param limit the number of records to return
     * @return a list of yield data
     */
    @Query("SELECT y FROM YieldData y ORDER BY y.yieldPerHectare DESC")
    List<YieldData> findTopByYieldPerHectare(Pageable pageable);

    /**
     * Find average yield per hectare by crop type.
     *
     * @param cropType the crop type
     * @return the average yield per hectare
     */
    @Query("SELECT AVG(y.yieldPerHectare) FROM YieldData y WHERE y.cropType = :cropType")
    Double findAverageYieldPerHectareByCropType(YieldData.CropType cropType);

    /**
     * Find yield data by farmer and crop type.
     *
     * @param farmer the farmer
     * @param cropType the crop type
     * @param pageable the pagination information
     * @return a page of yield data
     */
    Page<YieldData> findByFarmerAndCropType(Farmer farmer, YieldData.CropType cropType, Pageable pageable);
}