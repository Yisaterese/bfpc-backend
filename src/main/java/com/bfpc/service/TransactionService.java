package com.bfpc.service;

import com.bfpc.dto.TransactionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for managing transactions.
 */
public interface TransactionService {

    /**
     * Get all transactions with pagination.
     *
     * @param pageable the pagination information
     * @return a page of transactions
     */
    Page<TransactionDto> getAllTransactions(Pageable pageable);

    /**
     * Get a transaction by ID.
     *
     * @param id the transaction ID
     * @return the transaction
     */
    TransactionDto getTransactionById(Long id);

    /**
     * Create a new transaction.
     *
     * @param transactionDto the transaction to create
     * @return the created transaction
     */
    TransactionDto createTransaction(TransactionDto transactionDto);

    /**
     * Update a transaction.
     *
     * @param id the transaction ID
     * @param transactionDto the transaction to update
     * @return the updated transaction
     */
    TransactionDto updateTransaction(Long id, TransactionDto transactionDto);

    /**
     * Delete a transaction.
     *
     * @param id the transaction ID
     */
    void deleteTransaction(Long id);

    /**
     * Get transactions by farmer ID.
     *
     * @param farmerId the farmer ID
     * @param pageable the pagination information
     * @return a page of transactions
     */
    Page<TransactionDto> getTransactionsByFarmerId(Long farmerId, Pageable pageable);

    /**
     * Get transactions by buyer ID.
     *
     * @param buyerId the buyer ID
     * @param pageable the pagination information
     * @return a page of transactions
     */
    Page<TransactionDto> getTransactionsByBuyerId(Long buyerId, Pageable pageable);

    /**
     * Get transactions by status.
     *
     * @param status the transaction status
     * @param pageable the pagination information
     * @return a page of transactions
     */
    Page<TransactionDto> getTransactionsByStatus(String status, Pageable pageable);

    /**
     * Get transactions by crop type.
     *
     * @param cropType the crop type
     * @param pageable the pagination information
     * @return a page of transactions
     */
    Page<TransactionDto> getTransactionsByCropType(String cropType, Pageable pageable);

    /**
     * Get transactions by completion date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable the pagination information
     * @return a page of transactions
     */
    Page<TransactionDto> getTransactionsByCompletionDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable);

    /**
     * Get transactions by farmer ID and status.
     *
     * @param farmerId the farmer ID
     * @param status the transaction status
     * @param pageable the pagination information
     * @return a page of transactions
     */
    Page<TransactionDto> getTransactionsByFarmerIdAndStatus(Long farmerId, String status, Pageable pageable);

    /**
     * Get transactions by buyer ID and status.
     *
     * @param buyerId the buyer ID
     * @param status the transaction status
     * @param pageable the pagination information
     * @return a page of transactions
     */
    Page<TransactionDto> getTransactionsByBuyerIdAndStatus(Long buyerId, String status, Pageable pageable);

    /**
     * Get top transactions by total amount.
     *
     * @param limit the number of transactions to return
     * @return a list of transactions
     */
    List<TransactionDto> getTopTransactionsByTotalAmount(int limit);

    /**
     * Get total transaction amount by farmer ID.
     *
     * @param farmerId the farmer ID
     * @return the total transaction amount
     */
    Double getTotalTransactionAmountByFarmerId(Long farmerId);

    /**
     * Get total transaction amount by buyer ID.
     *
     * @param buyerId the buyer ID
     * @return the total transaction amount
     */
    Double getTotalTransactionAmountByBuyerId(Long buyerId);

    /**
     * Complete a transaction.
     *
     * @param id the transaction ID
     * @return the completed transaction
     */
    TransactionDto completeTransaction(Long id);

    /**
     * Cancel a transaction.
     *
     * @param id the transaction ID
     * @return the cancelled transaction
     */
    TransactionDto cancelTransaction(Long id);

    /**
     * Rate a transaction.
     *
     * @param id the transaction ID
     * @param farmerRating the farmer rating
     * @param buyerRating the buyer rating
     * @return the rated transaction
     */
    TransactionDto rateTransaction(Long id, Integer farmerRating, Integer buyerRating);
}