package com.bfpc.domain.repository;

import com.bfpc.domain.entity.Farmer;
import com.bfpc.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Farmer entity operations.
 */
@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Long> {

    /**
     * Find a farmer by associated user.
     *
     * @param user the user associated with the farmer
     * @return an Optional containing the farmer if found
     */
    Optional<Farmer> findByUser(User user);

    /**
     * Find a farmer by user ID.
     *
     * @param userId the ID of the user associated with the farmer
     * @return an Optional containing the farmer if found
     */
    @Query("SELECT f FROM Farmer f WHERE f.user.id = :userId")
    Optional<Farmer> findByUserId(Long userId);

    /**
     * Find farmers by local government area.
     *
     * @param lga the local government area to search for
     * @return a list of farmers in the specified local government area
     */
    @Query("SELECT f FROM Farmer f WHERE f.user.localGovernmentArea = :lga")
    List<Farmer> findByLocalGovernmentArea(String lga);

    /**
     * Find farmers by crop type.
     *
     * @param cropType the crop type to search for
     * @return a list of farmers growing the specified crop type
     */
    @Query("SELECT f FROM Farmer f JOIN f.cropTypes ct WHERE ct = :cropType")
    List<Farmer> findByCropType(Farmer.CropType cropType);

    /**
     * Find farmers eligible for sponsorship.
     *
     * @return a list of farmers eligible for sponsorship
     */
    List<Farmer> findByEligibleForSponsorshipTrue();

    /**
     * Find top farmers by average yield per hectare.
     *
     * @param limit the maximum number of farmers to return
     * @return a list of top farmers by average yield per hectare
     */
    @Query("SELECT f FROM Farmer f WHERE f.averageYieldPerHectare IS NOT NULL ORDER BY f.averageYieldPerHectare DESC")
    List<Farmer> findTopFarmersByYield(int limit);
}