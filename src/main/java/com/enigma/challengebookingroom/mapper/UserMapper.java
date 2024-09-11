package com.enigma.challengebookingroom.mapper;

import com.enigma.challengebookingroom.dto.response.Auth.LoginResponse;
import com.enigma.challengebookingroom.dto.response.Auth.RegisterResponse;

public interface UserMapper {
    LoginResponse toResponseLogin();

    RegisterResponse toRegisterResponse();
}
