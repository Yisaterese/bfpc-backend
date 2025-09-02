package com.bfpc.repository;

import com.bfpc.domain.entity.Buyer;
import com.bfpc.domain.entity.Farmer;
import com.bfpc.domain.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository for Transaction entity.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Find transactions by farmer.
     *
     * @param farmer the farmer
     * @param pageable the pagination information
     * @return a page of transactions
     */
    Page<Transaction> findByFarmer(Farmer farmer, Pageable pageable);

    /**
     * Find transactions by buyer.
     *
     * @param buyer the buyer
     * @param pageable the pagination information
     * @return a page of transactions
     */
    Page<Transaction> findByBuyer(Buyer buyer, Pageable pageable);

    /**
     * Find transactions by status.
     *
     * @param status the transaction status
     * @param pageable the pagination information
     * @return a page of transactions
     */
    Page<Transaction> findByStatus(Transaction.Status status, Pageable pageable);

    /**
     * Find transactions by crop type.
     *
     * @param cropType the crop type
     * @param pageable the pagination information
     * @return a page of transactions
     */
    Page<Transaction> findByCropType(Transaction.CropType cropType, Pageable pageable);

    /**
     * Find transactions by completion date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable the pagination information
     * @return a page of transactions
     */
    Page<Transaction> findByCompletionDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    /**
     * Find transactions by farmer and status.
     *
     * @param farmer the farmer
     * @param status the transaction status
     * @param pageable the pagination information
     * @return a page of transactions
     */
    Page<Transaction> findByFarmerAndStatus(Farmer farmer, Transaction.Status status, Pageable pageable);

    /**
     * Find transactions by buyer and status.
     *
     * @param buyer the buyer
     * @param status the transaction status
     * @param pageable the pagination information
     * @return a page of transactions
     */
    Page<Transaction> findByBuyerAndStatus(Buyer buyer, Transaction.Status status, Pageable pageable);

    /**
     * Find top transactions by total amount.
     *
     * @param pageable the pagination information
     * @return a list of transactions
     */
    @Query("SELECT t FROM Transaction t WHERE t.status = 'COMPLETED' ORDER BY t.totalAmount DESC")
    List<Transaction> findTopTransactionsByTotalAmount(Pageable pageable);

    /**
     * Find total transaction amount by farmer.
     *
     * @param farmer the farmer
     * @return the total transaction amount
     */
    @Query("SELECT SUM(t.totalAmount) FROM Transaction t WHERE t.farmer = :farmer AND t.status = 'COMPLETED'")
    Double findTotalTransactionAmountByFarmer(Farmer farmer);

    /**
     * Find total transaction amount by buyer.
     *
     * @param buyer the buyer
     * @return the total transaction amount
     */
    @Query("SELECT SUM(t.totalAmount) FROM Transaction t WHERE t.buyer = :buyer AND t.status = 'COMPLETED'")
    Double findTotalTransactionAmountByBuyer(Buyer buyer);

    /**
     * Find transactions by farmer ID.
     *
     * @param farmerId the farmer ID
     * @param pageable the pagination information
     * @return a page of transactions
     */
    Page<Transaction> findByFarmerId(Long farmerId, Pageable pageable);

    /**
     * Find transactions by buyer ID.
     *
     * @param buyerId the buyer ID
     * @param pageable the pagination information
     * @return a page of transactions
     */
    Page<Transaction> findByBuyerId(Long buyerId, Pageable pageable);
}