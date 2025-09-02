package com.bfpc.controller;

import com.bfpc.dto.BuyerDto;
import com.bfpc.service.BuyerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for buyer operations.
 */
@RestController
@RequestMapping("/api/buyers")
@RequiredArgsConstructor
public class BuyerController {

    private final BuyerService buyerService;

    /**
     * Get all buyers with pagination.
     *
     * @param pageable the pagination information
     * @return a page of buyers
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FARMER')")
    public ResponseEntity<Page<BuyerDto>> getAllBuyers(Pageable pageable) {
        return ResponseEntity.ok(buyerService.getAllBuyers(pageable));
    }

    /**
     * Get a buyer by ID.
     *
     * @param id the buyer ID
     * @return the buyer
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FARMER') or @buyerService.isBuyerOwner(#id, principal.username)")
    public ResponseEntity<BuyerDto> getBuyerById(@PathVariable Long id) {
        return ResponseEntity.ok(buyerService.getBuyerById(id));
    }

    /**
     * Get a buyer by user ID.
     *
     * @param userId the user ID
     * @return the buyer
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or @userService.isCurrentUser(#userId)")
    public ResponseEntity<BuyerDto> getBuyerByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(buyerService.getBuyerByUserId(userId));
    }

    /**
     * Update a buyer.
     *
     * @param id the buyer ID
     * @param buyerDto the buyer data
     * @return the updated buyer
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @buyerService.isBuyerOwner(#id, principal.username)")
    public ResponseEntity<BuyerDto> updateBuyer(
            @PathVariable Long id,
            @Valid @RequestBody BuyerDto buyerDto
    ) {
        return ResponseEntity.ok(buyerService.updateBuyer(id, buyerDto));
    }

    /**
     * Get buyers by verification status.
     *
     * @param verified the verification status
     * @param pageable the pagination information
     * @return a page of buyers
     */
    @GetMapping("/verified/{verified}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<BuyerDto>> getBuyersByVerificationStatus(
            @PathVariable boolean verified,
            Pageable pageable
    ) {
        return ResponseEntity.ok(buyerService.getBuyersByVerificationStatus(verified, pageable));
    }

    /**
     * Get buyers by crop interest.
     *
     * @param cropType the crop type
     * @param pageable the pagination information
     * @return a page of buyers
     */
    @GetMapping("/crop-interest/{cropType}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FARMER')")
    public ResponseEntity<Page<BuyerDto>> getBuyersByCropInterest(
            @PathVariable String cropType,
            Pageable pageable
    ) {
        return ResponseEntity.ok(buyerService.getBuyersByCropInterest(cropType, pageable));
    }

    /**
     * Get top buyers by rating.
     *
     * @param limit the number of buyers to return
     * @return a list of top buyers
     */
    @GetMapping("/top-by-rating")
    public ResponseEntity<List<BuyerDto>> getTopBuyersByRating(@RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(buyerService.getTopBuyersByRating(limit));
    }

    /**
     * Get top buyers by transaction count.
     *
     * @param limit the number of buyers to return
     * @return a list of top buyers
     */
    @GetMapping("/top-by-transactions")
    public ResponseEntity<List<BuyerDto>> getTopBuyersByTransactions(@RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(buyerService.getTopBuyersByTransactions(limit));
    }
}