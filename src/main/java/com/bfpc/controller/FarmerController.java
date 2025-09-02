package com.bfpc.controller;

import com.bfpc.domain.entity.Farmer;
import com.bfpc.domain.entity.YieldData;
import com.bfpc.dto.FarmerDto;
import com.bfpc.service.FarmerService;
import com.bfpc.service.YieldDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for farmer operations.
 */
@RestController
@RequestMapping("/api/farmers")
@RequiredArgsConstructor
public class FarmerController {

    private final FarmerService farmerService;
    private final YieldDataService yieldDataService;

    /**
     * Get all farmers with pagination.
     *
     * @param pageable the pagination information
     * @return a page of farmers
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'BUYER')")
    public ResponseEntity<Page<FarmerDto>> getAllFarmers(Pageable pageable) {
        return ResponseEntity.ok(farmerService.getAllFarmers(pageable));
    }

    /**
     * Get a farmer by ID.
     *
     * @param id the farmer ID
     * @return the farmer
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BUYER') or @farmerService.isFarmerOwner(#id, principal.username)")
    public ResponseEntity<FarmerDto> getFarmerById(@PathVariable Long id) {
        return ResponseEntity.ok(farmerService.getFarmerById(id));
    }

    /**
     * Get a farmer by user ID.
     *
     * @param userId the user ID
     * @return the farmer
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or @userService.isCurrentUser(#userId)")
    public ResponseEntity<FarmerDto> getFarmerByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(farmerService.getFarmerByUserId(userId));
    }

    /**
     * Update a farmer.
     *
     * @param id the farmer ID
     * @param farmerDto the farmer data
     * @return the updated farmer
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @farmerService.isFarmerOwner(#id, principal.username)")
    public ResponseEntity<FarmerDto> updateFarmer(
            @PathVariable Long id,
            @Valid @RequestBody FarmerDto farmerDto
    ) {
        return ResponseEntity.ok(farmerService.updateFarmer(id, farmerDto));
    }

    /**
     * Get farmers by crop type.
     *
     * @param cropType the crop type
     * @param pageable the pagination information
     * @return a page of farmers
     */
    @GetMapping("/crop/{cropType}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BUYER')")
    public ResponseEntity<Page<FarmerDto>> getFarmersByCropType(
            @PathVariable String cropType,
            Pageable pageable
    ) {
        return ResponseEntity.ok(farmerService.getFarmersByCropType(cropType, pageable));
    }

    /**
     * Get farmers by local government area.
     *
     * @param lga the local government area
     * @param pageable the pagination information
     * @return a page of farmers
     */
    @GetMapping("/lga/{lga}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BUYER')")
    public ResponseEntity<Page<FarmerDto>> getFarmersByLocalGovernmentArea(
            @PathVariable String lga,
            Pageable pageable
    ) {
        return ResponseEntity.ok(farmerService.getFarmersByLocalGovernmentArea(lga, pageable));
    }

    /**
     * Get top farmers by yield.
     *
     * @param limit the number of farmers to return
     * @return a list of top farmers
     */
    @GetMapping("/top-by-yield")
    public ResponseEntity<List<FarmerDto>> getTopFarmersByYield(@RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(farmerService.getTopFarmersByAverageYield(limit));
    }

    /**
     * Get yield data for a farmer.
     *
     * @param farmerId the farmer ID
     * @param pageable the pagination information
     * @return a page of yield data
     */
    @GetMapping("/{farmerId}/yields")
    @PreAuthorize("hasRole('ADMIN') or @farmerService.isFarmerOwner(#farmerId, principal.username)")
    public ResponseEntity<Page<YieldData>> getFarmerYieldData(
            @PathVariable Long farmerId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(yieldDataService.getYieldDataByFarmerId(farmerId, pageable));
    }

    /**
     * Get farmers eligible for sponsorship.
     *
     * @param pageable the pagination information
     * @return a page of farmers eligible for sponsorship
     */
    @GetMapping("/sponsorship-eligible")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<FarmerDto>> getSponsorshipEligibleFarmers(Pageable pageable) {
        return ResponseEntity.ok(farmerService.getSponsorshipEligibleFarmers(pageable));
    }
}