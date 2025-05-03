package com.me.security.doit.filters;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.me.security.doit.services.impl.JwtServiceImpl;
import com.me.security.doit.services.impl.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * A filter that intercepts incoming requests to validate JWT tokens.
 * This filter is applied once per request and checks for valid JWT tokens
 * in the Authorization header.
 * 
 * <p>If a valid token is found, it sets the authentication in the SecurityContext.</p>
 * 
 * @author KAYUMBA J88
 * @version 1.0
 * @see OncePerRequestFilter
 * @see Component
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    
    /**
     * Service for JWT token operations.
     */
    private final JwtServiceImpl jwtService;
    
    /**
     * Service for loading user details.
     */
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    
    /**
     * Constructs a new JwtAuthFilter with the required services.
     * 
     * @param jwtService the JWT service implementation
     * @param userDetailsServiceImpl the user details service implementation
     */
    public JwtAuthFilter(JwtServiceImpl jwtService, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.jwtService = jwtService;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }
    
    /**
     * Performs the actual filtering logic for JWT authentication.
     * 
     * @param request the HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain to continue processing
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            username = jwtService.extractUsername(token);
        }
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails user = userDetailsServiceImpl.loadUserByUsername(username);
            if (jwtService.isTokenValid(token, user)) {
                UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}