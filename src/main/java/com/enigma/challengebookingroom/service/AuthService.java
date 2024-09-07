package com.enigma.challengebookingroom.service;

import com.enigma.challengebookingroom.dto.request.LoginRequest;
import com.enigma.challengebookingroom.dto.request.RegisterRequest;
import com.enigma.challengebookingroom.dto.response.Auth.LoginResponse;
import com.enigma.challengebookingroom.dto.response.Auth.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest register);
    LoginResponse login(LoginRequest login);
}
