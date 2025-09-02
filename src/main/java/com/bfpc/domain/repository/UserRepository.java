package com.bfpc.domain.repository;

import com.bfpc.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entity operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by email.
     *
     * @param email the email to search for
     * @return an Optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Find a user by phone number.
     *
     * @param phoneNumber the phone number to search for
     * @return an Optional containing the user if found
     */
    Optional<User> findByPhoneNumber(String phoneNumber);

    /**
     * Check if a user exists with the given email.
     *
     * @param email the email to check
     * @return true if a user exists with the email, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Check if a user exists with the given phone number.
     *
     * @param phoneNumber the phone number to check
     * @return true if a user exists with the phone number, false otherwise
     */
    boolean existsByPhoneNumber(String phoneNumber);
}