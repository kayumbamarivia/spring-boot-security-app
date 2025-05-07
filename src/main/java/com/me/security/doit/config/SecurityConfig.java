package com.me.security.doit.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.me.security.doit.filters.JwtAuthFilter;
import com.me.security.doit.services.impl.UserDetailsServiceImpl;

/**
 * Main security configuration class for the application.
 * Configures authentication, authorization, and security filters.
 * 
 * <p>This class enables web security, configures CSRF protection, session management,
 * and defines security filter chains.</p>
 * 
 * @author KAYUMBA J88
 * @version 1.0
 * @see Configuration
 * @see EnableWebSecurity
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    /**
     * Service for loading user details.
     */
    private final UserDetailsServiceImpl user;
    
    /**
     * Filter for JWT authentication.
     */
    private final JwtAuthFilter jwtAuthFilter;
    private static final String[] WHITE_LIST = {
        "/api/v1/auth/**",
        "/actuator/metrics",
        "/actuator/health",
        "/actuator/metrics/**",
        "/v2/api-docs",
        "/v3/api-docs",
        "/v3/api-docs/**",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui/**",
        "/webjars/**",
        "/swagger-ui.html"
    };
    
    /**
     * Constructs a new SecurityConfig with required dependencies.
     * 
     * @param user the user details service implementation
     * @param jwtAuthFilter the JWT authentication filter
     */
    public SecurityConfig(UserDetailsServiceImpl user, JwtAuthFilter jwtAuthFilter) {
        this.user = user;
        this.jwtAuthFilter = jwtAuthFilter;
    }
    
    /**
     * Configures the security filter chain.
     * 
     * @param http the HttpSecurity to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    WHITE_LIST
                ).permitAll()
                .requestMatchers("/api/v1/users/**").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/user/**").hasAnyAuthority("USER", "ADMIN")
                .anyRequest().authenticated()
            )
            .userDetailsService(user)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .build();          
    }
    
    /**
     * Creates an AuthenticationManager bean.
     * 
     * @param conf the AuthenticationConfiguration
     * @return the AuthenticationManager
     * @throws Exception if an error occurs during creation
     */
    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration conf) throws Exception {
        return conf.getAuthenticationManager();
    }
    
    /**
     * Creates a PasswordEncoder bean for password hashing.
     * 
     * @return the PasswordEncoder implementation (BCrypt)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of( "http://localhost:5173")); // Configurable origins
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        config.setAllowCredentials(true);
        config.setExposedHeaders(List.of("Authorization"));
        config.setMaxAge(3600L); 
    
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config); // Apply to API endpoints only
        return source;
    }
}