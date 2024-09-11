package com.enigma.challengebookingroom.mapper.impl;

import com.enigma.challengebookingroom.dto.response.Auth.LoginResponse;
import com.enigma.challengebookingroom.dto.response.Auth.RegisterResponse;
import com.enigma.challengebookingroom.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public LoginResponse toResponseLogin() {
        return null;
    }

    @Override
    public RegisterResponse toRegisterResponse() {
        return null;
    }
}
