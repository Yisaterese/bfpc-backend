package com.bfpc.service;

import com.bfpc.dto.BuyerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for managing buyers.
 */
public interface BuyerService {

    /**
     * Get all buyers with pagination.
     *
     * @param pageable the pagination information
     * @return a page of buyers
     */
    Page<BuyerDto> getAllBuyers(Pageable pageable);

    /**
     * Get a buyer by ID.
     *
     * @param id the buyer ID
     * @return the buyer
     */
    BuyerDto getBuyerById(Long id);

    /**
     * Get a buyer by user ID.
     *
     * @param userId the user ID
     * @return the buyer
     */
    BuyerDto getBuyerByUserId(Long userId);

    /**
     * Update a buyer.
     *
     * @param id the buyer ID
     * @param buyerDto the buyer data
     * @return the updated buyer
     */
    BuyerDto updateBuyer(Long id, BuyerDto buyerDto);

    /**
     * Get buyers by verification status.
     *
     * @param verified the verification status
     * @param pageable the pagination information
     * @return a page of buyers
     */
    Page<BuyerDto> getBuyersByVerificationStatus(boolean verified, Pageable pageable);

    /**
     * Get buyers by crop interest.
     *
     * @param cropType the crop type
     * @param pageable the pagination information
     * @return a page of buyers
     */
    Page<BuyerDto> getBuyersByCropInterest(String cropType, Pageable pageable);

    /**
     * Get top buyers by rating.
     *
     * @param limit the number of buyers to return
     * @return a list of top buyers
     */
    List<BuyerDto> getTopBuyersByRating(int limit);

    /**
     * Get top buyers by transaction count.
     *
     * @param limit the number of buyers to return
     * @return a list of top buyers
     */
    List<BuyerDto> getTopBuyersByTransactions(int limit);

    /**
     * Check if the current user is the owner of the buyer.
     *
     * @param buyerId the buyer ID
     * @param username the username
     * @return true if the current user is the owner of the buyer
     */
    boolean isBuyerOwner(Long buyerId, String username);
}