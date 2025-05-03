package com.me.security.doit.services;

import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import com.me.security.doit.models.User;

import io.jsonwebtoken.Claims;

public interface JwtService {
    String extractUsername(String token);
    String generateToken(User user);
    boolean isTokenValid(String token, UserDetails userDetails);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
}
