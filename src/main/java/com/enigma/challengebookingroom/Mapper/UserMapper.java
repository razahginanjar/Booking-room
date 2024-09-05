package com.enigma.challengebookingroom.Mapper;

import com.enigma.challengebookingroom.dto.response.Auth.LoginResponse;
import com.enigma.challengebookingroom.dto.response.Auth.RegisterResponse;

public interface UserMapper {
    LoginResponse toResponseLogin();
    RegisterResponse toRegisterResponse();
}
