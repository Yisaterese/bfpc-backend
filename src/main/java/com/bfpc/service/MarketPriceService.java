package com.bfpc.service;

import com.bfpc.dto.MarketPriceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

/**
 * Service interface for market price operations.
 */
public interface MarketPriceService {

    /**
     * Get all market prices with pagination.
     *
     * @param pageable the pagination information
     * @return a page of market prices
     */
    Page<MarketPriceDto> getAllMarketPrices(Pageable pageable);

    /**
     * Get a market price by ID.
     *
     * @param id the market price ID
     * @return the market price
     */
    MarketPriceDto getMarketPriceById(Long id);

    /**
     * Create a new market price.
     *
     * @param marketPriceDto the market price to create
     * @return the created market price
     */
    MarketPriceDto createMarketPrice(MarketPriceDto marketPriceDto);

    /**
     * Update a market price.
     *
     * @param id the market price ID
     * @param marketPriceDto the market price to update
     * @return the updated market price
     */
    MarketPriceDto updateMarketPrice(Long id, MarketPriceDto marketPriceDto);

    /**
     * Delete a market price.
     *
     * @param id the market price ID
     */
    void deleteMarketPrice(Long id);

    /**
     * Get market prices by crop type.
     *
     * @param cropType the crop type
     * @param pageable the pagination information
     * @return a page of market prices
     */
    Page<MarketPriceDto> getMarketPricesByCropType(String cropType, Pageable pageable);

    /**
     * Get market prices by date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable the pagination information
     * @return a page of market prices
     */
    Page<MarketPriceDto> getMarketPricesByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable);

    /**
     * Get market prices by location.
     *
     * @param location the location
     * @param pageable the pagination information
     * @return a page of market prices
     */
    Page<MarketPriceDto> getMarketPricesByLocation(String location, Pageable pageable);

    /**
     * Get market price trends.
     *
     * @param cropType the crop type (optional)
     * @param months the number of months to analyze
     * @return the market price trends
     */
    Object getMarketPriceTrends(String cropType, Integer months);
}