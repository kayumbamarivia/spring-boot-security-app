package com.me.security.doit.services.impl;

import org.springframework.stereotype.Service;
import com.me.security.doit.services.JwtService;
import com.me.security.doit.config.JwtConfig;
import java.util.Date;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.function.Function;
import javax.crypto.SecretKey;
import com.me.security.doit.models.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Implementation of JwtService for JWT token operations.
 * Handles token generation, validation, and claims extraction.
 * 
 * @author KAYUMBA J88
 * @version 1.0
 * @see Service
 * @see JwtService
 */
@Service
public class JwtServiceImpl implements JwtService {
    
    /**
     * Configuration for JWT settings.
     */
    private final JwtConfig jwtConfig;

    /**
     * Constructs a new JwtServiceImpl with JWT configuration.
     * 
     * @param jwtConfig the JWT configuration
     */
    public JwtServiceImpl(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }
    
    /**
     * Checks if a token is expired.
     * 
     * @param token the JWT token to check
     * @return true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    
    /**
     * Extracts the expiration date from a token.
     * 
     * @param token the JWT token
     * @return the expiration date
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    /**
     * Validates a JWT token against user details.
     * 
     * @param token the JWT token to validate
     * @param user the user details to validate against
     * @return true if the token is valid for the user, false otherwise
     */
    @Override
    public boolean isTokenValid(String token, UserDetails user){
        final String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }
    
    /**
     * Extracts the username from a JWT token.
     * 
     * @param token the JWT token
     * @return the username
     */
    @Override
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }
    
    /**
     * Extracts a specific claim from a JWT token.
     * 
     * @param <T> the type of the claim
     * @param token the JWT token
     * @param claimsResolver function to resolve the claim
     * @return the extracted claim
     */
    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    /**
     * Extracts all claims from a JWT token.
     * 
     * @param token the JWT token
     * @return the claims
     */
    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Generates a JWT token for a user.
     * 
     * @param user the user to generate token for
     * @return the generated JWT token
     */
    @Override
    public String generateToken(User user){
        return Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
                .signWith(getSigninKey())
                .compact();
    }

    /**
     * Gets the signing key for JWT tokens.
     * 
     * @return the secret key
     */
    private SecretKey getSigninKey(){
        byte[] keyBytes = Decoders.BASE64URL.decode(jwtConfig.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}