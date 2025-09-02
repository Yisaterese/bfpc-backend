package com.bfpc.service;

import com.bfpc.domain.entity.Farmer;
import com.bfpc.dto.FarmerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for managing farmers.
 */
public interface FarmerService {

    /**
     * Get all farmers with pagination.
     *
     * @param pageable the pagination information
     * @return a page of farmers
     */
    Page<FarmerDto> getAllFarmers(Pageable pageable);

    /**
     * Get a farmer by ID.
     *
     * @param id the farmer ID
     * @return the farmer
     */
    FarmerDto getFarmerById(Long id);

    /**
     * Get a farmer by user ID.
     *
     * @param userId the user ID
     * @return the farmer
     */
    FarmerDto getFarmerByUserId(Long userId);

    /**
     * Update a farmer.
     *
     * @param id the farmer ID
     * @param farmerDto the farmer data
     * @return the updated farmer
     */
    FarmerDto updateFarmer(Long id, FarmerDto farmerDto);

    /**
     * Get farmers by crop type.
     *
     * @param cropType the crop type
     * @param pageable the pagination information
     * @return a page of farmers
     */
    Page<FarmerDto> getFarmersByCropType(String cropType, Pageable pageable);

    /**
     * Get farmers by local government area.
     *
     * @param lga the local government area
     * @param pageable the pagination information
     * @return a page of farmers
     */
    Page<FarmerDto> getFarmersByLocalGovernmentArea(String lga, Pageable pageable);

    /**
     * Get top farmers by average yield.
     *
     * @param limit the number of farmers to return
     * @return a list of top farmers
     */
    List<FarmerDto> getTopFarmersByAverageYield(int limit);

    /**
     * Get farmers eligible for sponsorship.
     *
     * @param pageable the pagination information
     * @return a page of farmers eligible for sponsorship
     */
    Page<FarmerDto> getSponsorshipEligibleFarmers(Pageable pageable);

    /**
     * Check if the current user is the owner of the farmer.
     *
     * @param farmerId the farmer ID
     * @param username the username
     * @return true if the current user is the owner of the farmer
     */
    boolean isFarmerOwner(Long farmerId, String username);
}