package com.me.security.doit.services;

import java.util.List;

import com.me.security.doit.dtos.LoginRequest;
import com.me.security.doit.dtos.RegisterRequest;
import com.me.security.doit.models.User;

public interface UserService {
    User login (LoginRequest loginRequest);
    User register (RegisterRequest registerRequest);
    List<User> getAllUsers ();
}
