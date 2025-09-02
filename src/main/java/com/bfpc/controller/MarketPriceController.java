package com.bfpc.controller;

import com.bfpc.dto.MarketPriceDto;
import com.bfpc.service.MarketPriceService;
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
 * Controller for market price operations.
 */
@RestController
@RequestMapping("/api/market-prices")
@RequiredArgsConstructor
public class MarketPriceController {

    private final MarketPriceService marketPriceService;

    /**
     * Get all market prices with pagination.
     *
     * @param pageable the pagination information
     * @return a page of market prices
     */
    @GetMapping
    public ResponseEntity<Page<MarketPriceDto>> getAllMarketPrices(Pageable pageable) {
        return ResponseEntity.ok(marketPriceService.getAllMarketPrices(pageable));
    }

    /**
     * Get a market price by ID.
     *
     * @param id the market price ID
     * @return the market price
     */
    @GetMapping("/{id}")
    public ResponseEntity<MarketPriceDto> getMarketPriceById(@PathVariable Long id) {
        return ResponseEntity.ok(marketPriceService.getMarketPriceById(id));
    }

    /**
     * Create a new market price.
     *
     * @param marketPriceDto the market price to create
     * @return the created market price
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MarketPriceDto> createMarketPrice(
            @Valid @RequestBody MarketPriceDto marketPriceDto
    ) {
        return ResponseEntity.ok(marketPriceService.createMarketPrice(marketPriceDto));
    }

    /**
     * Update a market price.
     *
     * @param id the market price ID
     * @param marketPriceDto the market price to update
     * @return the updated market price
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MarketPriceDto> updateMarketPrice(
            @PathVariable Long id,
            @Valid @RequestBody MarketPriceDto marketPriceDto
    ) {
        return ResponseEntity.ok(marketPriceService.updateMarketPrice(id, marketPriceDto));
    }

    /**
     * Delete a market price.
     *
     * @param id the market price ID
     * @return no content
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMarketPrice(@PathVariable Long id) {
        marketPriceService.deleteMarketPrice(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get market prices by crop type.
     *
     * @param cropType the crop type
     * @param pageable the pagination information
     * @return a page of market prices
     */
    @GetMapping("/crop-type/{cropType}")
    public ResponseEntity<Page<MarketPriceDto>> getMarketPricesByCropType(
            @PathVariable String cropType,
            Pageable pageable
    ) {
        return ResponseEntity.ok(marketPriceService.getMarketPricesByCropType(cropType, pageable));
    }

    /**
     * Get market prices by date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable the pagination information
     * @return a page of market prices
     */
    @GetMapping("/date-range")
    public ResponseEntity<Page<MarketPriceDto>> getMarketPricesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Pageable pageable
    ) {
        return ResponseEntity.ok(marketPriceService.getMarketPricesByDateRange(startDate, endDate, pageable));
    }

    /**
     * Get market prices by location.
     *
     * @param location the location
     * @param pageable the pagination information
     * @return a page of market prices
     */
    @GetMapping("/location/{location}")
    public ResponseEntity<Page<MarketPriceDto>> getMarketPricesByLocation(
            @PathVariable String location,
            Pageable pageable
    ) {
        return ResponseEntity.ok(marketPriceService.getMarketPricesByLocation(location, pageable));
    }

    /**
     * Get market price trends.
     *
     * @param cropType the crop type
     * @param months the number of months to analyze
     * @return the market price trends
     */
    @GetMapping("/trends")
    public ResponseEntity<Object> getMarketPriceTrends(
            @RequestParam(required = false) String cropType,
            @RequestParam(defaultValue = "6") Integer months
    ) {
        return ResponseEntity.ok(marketPriceService.getMarketPriceTrends(cropType, months));
    }
}