package com.me.security.doit.services.impl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.me.security.doit.repositories.UserRepo;

/**
 * Implementation of UserDetailsService for loading user-specific data.
 * 
 * <p>This service is used by Spring Security to authenticate and authorize users.</p>
 * 
 * @author KAYUMBA J88
 * @version 1.0
 * @see Service
 * @see UserDetailsService
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    /**
     * Repository for user data access.
     */
    private final UserRepo userRepository;
    
    /**
     * Constructs a new UserDetailsServiceImpl with the user repository.
     * 
     * @param repo the user repository
     */
    public UserDetailsServiceImpl(UserRepo repo) {
        this.userRepository = repo;
    }
    
    /**
     * Loads the user by username.
     * 
     * @param username the username to search for
     * @return the UserDetails implementation for the found user
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    }
}