package com.bfpc.controller;

import com.bfpc.dto.TransactionDto;
import com.bfpc.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller for transaction operations.
 */
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * Get all transactions with pagination.
     *
     * @param pageable the pagination information
     * @return a page of transactions
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<TransactionDto>> getAllTransactions(Pageable pageable) {
        return ResponseEntity.ok(transactionService.getAllTransactions(pageable));
    }

    /**
     * Get a transaction by ID.
     *
     * @param id the transaction ID
     * @return the transaction
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @transactionService.isTransactionParticipant(#id, principal.username)")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    /**
     * Create a new transaction.
     *
     * @param transactionDto the transaction to create
     * @return the created transaction
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FARMER', 'BUYER')")
    public ResponseEntity<TransactionDto> createTransaction(
            @Valid @RequestBody TransactionDto transactionDto
    ) {
        return ResponseEntity.ok(transactionService.createTransaction(transactionDto));
    }

    /**
     * Update a transaction.
     *
     * @param id the transaction ID
     * @param transactionDto the transaction to update
     * @return the updated transaction
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @transactionService.isTransactionParticipant(#id, principal.username)")
    public ResponseEntity<TransactionDto> updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody TransactionDto transactionDto
    ) {
        return ResponseEntity.ok(transactionService.updateTransaction(id, transactionDto));
    }

    /**
     * Delete a transaction.
     *
     * @param id the transaction ID
     * @return no content
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get transactions by farmer ID.
     *
     * @param farmerId the farmer ID
     * @param pageable the pagination information
     * @return a page of transactions
     */
    @GetMapping("/farmer/{farmerId}")
    @PreAuthorize("hasRole('ADMIN') or @farmerService.isFarmerOwner(#farmerId, principal.username)")
    public ResponseEntity<Page<TransactionDto>> getTransactionsByFarmerId(
            @PathVariable Long farmerId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(transactionService.getTransactionsByFarmerId(farmerId, pageable));
    }

    /**
     * Get transactions by buyer ID.
     *
     * @param buyerId the buyer ID
     * @param pageable the pagination information
     * @return a page of transactions
     */
    @GetMapping("/buyer/{buyerId}")
    @PreAuthorize("hasRole('ADMIN') or @buyerService.isBuyerOwner(#buyerId, principal.username)")
    public ResponseEntity<Page<TransactionDto>> getTransactionsByBuyerId(
            @PathVariable Long buyerId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(transactionService.getTransactionsByBuyerId(buyerId, pageable));
    }

    /**
     * Get transactions by status.
     *
     * @param status the transaction status
     * @param pageable the pagination information
     * @return a page of transactions
     */
    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<TransactionDto>> getTransactionsByStatus(
            @PathVariable String status,
            Pageable pageable
    ) {
        return ResponseEntity.ok(transactionService.getTransactionsByStatus(status, pageable));
    }

    /**
     * Get transactions by crop type.
     *
     * @param cropType the crop type
     * @param pageable the pagination information
     * @return a page of transactions
     */
    @GetMapping("/crop/{cropType}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FARMER', 'BUYER')")
    public ResponseEntity<Page<TransactionDto>> getTransactionsByCropType(
            @PathVariable String cropType,
            Pageable pageable
    ) {
        return ResponseEntity.ok(transactionService.getTransactionsByCropType(cropType, pageable));
    }

    /**
     * Get transactions by completion date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable the pagination information
     * @return a page of transactions
     */
    @GetMapping("/date-range")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<TransactionDto>> getTransactionsByCompletionDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Pageable pageable
    ) {
        return ResponseEntity.ok(transactionService.getTransactionsByCompletionDateRange(startDate, endDate, pageable));
    }

    /**
     * Get transactions by farmer ID and status.
     *
     * @param farmerId the farmer ID
     * @param status the transaction status
     * @param pageable the pagination information
     * @return a page of transactions
     */
    @GetMapping("/farmer/{farmerId}/status/{status}")
    @PreAuthorize("hasRole('ADMIN') or @farmerService.isFarmerOwner(#farmerId, principal.username)")
    public ResponseEntity<Page<TransactionDto>> getTransactionsByFarmerIdAndStatus(
            @PathVariable Long farmerId,
            @PathVariable String status,
            Pageable pageable
    ) {
        return ResponseEntity.ok(transactionService.getTransactionsByFarmerIdAndStatus(farmerId, status, pageable));
    }

    /**
     * Get transactions by buyer ID and status.
     *
     * @param buyerId the buyer ID
     * @param status the transaction status
     * @param pageable the pagination information
     * @return a page of transactions
     */
    @GetMapping("/buyer/{buyerId}/status/{status}")
    @PreAuthorize("hasRole('ADMIN') or @buyerService.isBuyerOwner(#buyerId, principal.username)")
    public ResponseEntity<Page<TransactionDto>> getTransactionsByBuyerIdAndStatus(
            @PathVariable Long buyerId,
            @PathVariable String status,
            Pageable pageable
    ) {
        return ResponseEntity.ok(transactionService.getTransactionsByBuyerIdAndStatus(buyerId, status, pageable));
    }

    /**
     * Get top transactions by total amount.
     *
     * @param limit the number of transactions to return
     * @return a list of transactions
     */
    @GetMapping("/top-by-amount")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TransactionDto>> getTopTransactionsByTotalAmount(
            @RequestParam(defaultValue = "10") int limit
    ) {
        return ResponseEntity.ok(transactionService.getTopTransactionsByTotalAmount(limit));
    }

    /**
     * Get total transaction amount by farmer ID.
     *
     * @param farmerId the farmer ID
     * @return the total transaction amount
     */
    @GetMapping("/farmer/{farmerId}/total-amount")
    @PreAuthorize("hasRole('ADMIN') or @farmerService.isFarmerOwner(#farmerId, principal.username)")
    public ResponseEntity<Double> getTotalTransactionAmountByFarmerId(@PathVariable Long farmerId) {
        return ResponseEntity.ok(transactionService.getTotalTransactionAmountByFarmerId(farmerId));
    }

    /**
     * Get total transaction amount by buyer ID.
     *
     * @param buyerId the buyer ID
     * @return the total transaction amount
     */
    @GetMapping("/buyer/{buyerId}/total-amount")
    @PreAuthorize("hasRole('ADMIN') or @buyerService.isBuyerOwner(#buyerId, principal.username)")
    public ResponseEntity<Double> getTotalTransactionAmountByBuyerId(@PathVariable Long buyerId) {
        return ResponseEntity.ok(transactionService.getTotalTransactionAmountByBuyerId(buyerId));
    }

    /**
     * Complete a transaction.
     *
     * @param id the transaction ID
     * @return the completed transaction
     */
    @PutMapping("/{id}/complete")
    @PreAuthorize("hasRole('ADMIN') or @transactionService.isTransactionParticipant(#id, principal.username)")
    public ResponseEntity<TransactionDto> completeTransaction(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.completeTransaction(id));
    }

    /**
     * Cancel a transaction.
     *
     * @param id the transaction ID
     * @return the cancelled transaction
     */
    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasRole('ADMIN') or @transactionService.isTransactionParticipant(#id, principal.username)")
    public ResponseEntity<TransactionDto> cancelTransaction(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.cancelTransaction(id));
    }

    /**
     * Rate a transaction.
     *
     * @param id the transaction ID
     * @param farmerRating the farmer rating
     * @param buyerRating the buyer rating
     * @return the rated transaction
     */
    @PutMapping("/{id}/rate")
    @PreAuthorize("hasRole('ADMIN') or @transactionService.isTransactionParticipant(#id, principal.username)")
    public ResponseEntity<TransactionDto> rateTransaction(
            @PathVariable Long id,
            @RequestParam(required = false) Integer farmerRating,
            @RequestParam(required = false) Integer buyerRating
    ) {
        return ResponseEntity.ok(transactionService.rateTransaction(id, farmerRating, buyerRating));
    }
}