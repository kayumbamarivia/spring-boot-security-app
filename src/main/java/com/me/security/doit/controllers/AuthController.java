package com.me.security.doit.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.me.security.doit.dtos.LoginRequest;
import com.me.security.doit.dtos.RegisterRequest;
import com.me.security.doit.models.User;
import com.me.security.doit.services.impl.JwtServiceImpl;
import com.me.security.doit.services.impl.UserServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controller for authentication and user-related operations.
 * Handles user registration, login, and retrieval of users.
 * 
 * @author KAYUMBA J88
 * @version 1.0
 * @see RestController
 * @see RequestMapping
 */
@RestController
@RequestMapping("/api/v1/")
public class AuthController {
    
    /**
     * Service for user operations.
     */
    private final UserServiceImpl service;
    
    /**
     * Service for JWT operations.
     */
    private final JwtServiceImpl jwtService;

    /**
     * Constructs a new AuthController with required services.
     * 
     * @param service the user service implementation
     * @param jwtService the JWT service implementation
     */
    public AuthController(UserServiceImpl service, JwtServiceImpl jwtService) {
        this.service = service;
        this.jwtService = jwtService;
    }

    /**
     * Authenticates a user and returns a JWT token.
     * 
     * @param request the login request containing credentials
     * @return ResponseEntity containing user details and JWT token
     */
    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, Object>> authenticate(@RequestBody LoginRequest request) {
        User user = service.login(request);
        String token = jwtService.generateToken(user);
        Map<String, Object> response = new HashMap<>();
        response.put("user", user);
        response.put("token", token);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * Registers a new user.
     * 
     * @param request the registration request containing user details
     * @return ResponseEntity containing the registered user
     */
    @PostMapping(value = {"/auth/register", "/auth/signup"})
    public ResponseEntity<User> register(@RequestBody RegisterRequest request){
        User response = service.register(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all users.
     * 
     * @return list of all users
     */
    @GetMapping("/users")
    public List<User> getAllUsers(){
        return service.getAllUsers();
    }
}