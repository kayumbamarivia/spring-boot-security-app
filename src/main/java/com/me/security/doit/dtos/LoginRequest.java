package com.me.security.doit.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for login requests.
 * Contains validation constraints for username and password.
 * 
 * @author KAYUMBA J88
 * @version 1.0
 * @see NotBlank
 * @see Size
 */
public record LoginRequest(
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    String username,
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    String password
) {
}


