package com.me.security.doit.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.me.security.doit.models.User;

/**
 * Repository interface for User entities.
 * Extends JpaRepository to provide CRUD operations for User objects.
 * 
 * @author KAYUMBA J88
 * @version 1.0
 * @see JpaRepository
 */
public interface UserRepo extends JpaRepository<User, Long> {
    
    /**
     * Finds a user by username.
     * 
     * @param username the username to search for
     * @return an Optional containing the user if found
     */
    Optional<User> findByUsername(String username);
}