package com.me.security.doit.dtos;
import com.me.security.doit.models.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for user registration requests.
 * Contains validation constraints for user registration fields.
 * 
 * @author KAYUMBA J88
 * @version 1.0
 * @see NotBlank
 * @see Size
 */
public record RegisterRequest(
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    String name,
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    String username,
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    String password,
    @NotBlank(message = "Role is required")
    @Size(min = 3, max = 20, message = "Role must be between 3 and 5 characters")
    Role role
) {
}