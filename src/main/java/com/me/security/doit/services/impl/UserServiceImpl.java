package com.me.security.doit.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.me.security.doit.dtos.LoginRequest;
import com.me.security.doit.dtos.RegisterRequest;
import com.me.security.doit.models.User;
import com.me.security.doit.repositories.UserRepo;
import com.me.security.doit.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepo userRepository, PasswordEncoder passwordEncoder,
        AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public User login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        return userRepository.findByUsername(loginRequest.username())
            .orElseThrow(() -> new RuntimeException("User not found"));
    }
    @Override
    public User register(RegisterRequest registerRequest) {
       Optional<User> existingUser = userRepository.findByUsername(registerRequest.username());
        if (existingUser.isPresent()) {
           return null;
        }
        User user = new User();
        user.setName(registerRequest.name());
        user.setUsername(registerRequest.username());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        user.setRole(registerRequest.role());
        return userRepository.save(user);
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
