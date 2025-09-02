package com.bfpc.repository;

import com.bfpc.domain.entity.MarketPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository for market price operations.
 */
@Repository
public interface MarketPriceRepository extends JpaRepository<MarketPrice, Long> {

    /**
     * Find market prices by crop type.
     *
     * @param cropType the crop type
     * @param pageable the pagination information
     * @return a page of market prices
     */
    Page<MarketPrice> findByCropTypeIgnoreCase(String cropType, Pageable pageable);

    /**
     * Find market prices by date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable the pagination information
     * @return a page of market prices
     */
    Page<MarketPrice> findByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    /**
     * Find market prices by location.
     *
     * @param location the location
     * @param pageable the pagination information
     * @return a page of market prices
     */
    Page<MarketPrice> findByLocationIgnoreCase(String location, Pageable pageable);

    /**
     * Find market prices by crop type and date range.
     *
     * @param cropType the crop type
     * @param startDate the start date
     * @param endDate the end date
     * @return a list of market prices
     */
    List<MarketPrice> findByCropTypeIgnoreCaseAndDateBetweenOrderByDateAsc(String cropType, LocalDate startDate, LocalDate endDate);

    /**
     * Find latest market prices by crop type.
     *
     * @param cropType the crop type
     * @param pageable the pagination information
     * @return a page of market prices
     */
    Page<MarketPrice> findByCropTypeIgnoreCaseOrderByDateDesc(String cropType, Pageable pageable);

    /**
     * Find latest market prices.
     *
     * @param pageable the pagination information
     * @return a page of market prices
     */
    Page<MarketPrice> findAllByOrderByDateDesc(Pageable pageable);
}