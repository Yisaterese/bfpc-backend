package com.bfpc.domain.repository;

import com.bfpc.domain.entity.Buyer;
import com.bfpc.domain.entity.Farmer;
import com.bfpc.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Buyer entity operations.
 */
@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {

    /**
     * Find a buyer by associated user.
     *
     * @param user the user associated with the buyer
     * @return an Optional containing the buyer if found
     */
    Optional<Buyer> findByUser(User user);

    /**
     * Find a buyer by user ID.
     *
     * @param userId the ID of the user associated with the buyer
     * @return an Optional containing the buyer if found
     */
    @Query("SELECT b FROM Buyer b WHERE b.user.id = :userId")
    Optional<Buyer> findByUserId(Long userId);

    /**
     * Find buyers by verification status.
     *
     * @param verified the verification status to search for
     * @return a list of buyers with the specified verification status
     */
    List<Buyer> findByVerified(Boolean verified);

    /**
     * Find buyers by crop interest.
     *
     * @param cropType the crop type to search for
     * @return a list of buyers interested in the specified crop type
     */
    @Query("SELECT b FROM Buyer b JOIN b.cropInterests ci WHERE ci = :cropType")
    List<Buyer> findByCropInterest(Farmer.CropType cropType);

    /**
     * Find top buyers by number of successful transactions.
     *
     * @param limit the maximum number of buyers to return
     * @return a list of top buyers by number of successful transactions
     */
    @Query("SELECT b FROM Buyer b WHERE b.successfulTransactions IS NOT NULL ORDER BY b.successfulTransactions DESC")
    List<Buyer> findTopBuyersByTransactions(int limit);

    /**
     * Find top buyers by average rating.
     *
     * @param limit the maximum number of buyers to return
     * @return a list of top buyers by average rating
     */
    @Query("SELECT b FROM Buyer b WHERE b.averageRating IS NOT NULL ORDER BY b.averageRating DESC")
    List<Buyer> findTopBuyersByRating(int limit);
}