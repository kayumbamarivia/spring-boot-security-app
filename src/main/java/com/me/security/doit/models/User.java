package com.me.security.doit.models;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a user entity in the system, implementing Spring Security's UserDetails interface.
 * This class maps to the "users" table in the database and provides user authentication details.
 * 
 * <p>The class includes Lombok annotations for boilerplate code reduction and JPA annotations
 * for database mapping.</p>
 * 
 * @author KAYUMBA J88
 * @version 1.0
 * @since 2024
 * @see UserDetails
 * @see Entity
 */
@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    
    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * The full name of the user.
     */
    private String name;
    
    /**
     * The username used for authentication.
     * Must be unique across the system.
     */
    private String username;
    
    /**
     * The encrypted password for the user.
     * Stored in BCrypt format.
     */
    private String password;
    
    /**
     * The role of the user in the system.
     * Determines the user's authorities and access levels.
     */
    @Enumerated(EnumType.STRING)
    private Role role;
    
    /**
     * Returns the authorities granted to the user.
     * 
     * @return a collection of GrantedAuthority objects representing the user's authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    
    /**
     * Indicates whether the user's account has expired.
     * 
     * @return true if the account is valid (non-expired), false otherwise
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    /**
     * Indicates whether the user is locked or unlocked.
     * 
     * @return true if the user is not locked, false otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    /**
     * Indicates whether the user's credentials (password) has expired.
     * 
     * @return true if the credentials are valid (non-expired), false otherwise
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    /**
     * Indicates whether the user is enabled or disabled.
     * 
     * @return true if the user is enabled, false otherwise
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}