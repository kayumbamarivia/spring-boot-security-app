package com.me.security.doit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**
 * Configuration properties for JWT token handling.
 * 
 * <p>Loads JWT settings from application properties:</p>
 * <ul>
 *   <li>Secret key for signing tokens</li>
 *   <li>Token expiration time</li>
 * </ul>
 */
@Component
public class JwtConfig {
    private final String secretKey;
    public JwtConfig(@Value("${jwt.secret}") String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public long getExpiration() {
        return 1000L * 60 * 60 * 10; 
    }
}
