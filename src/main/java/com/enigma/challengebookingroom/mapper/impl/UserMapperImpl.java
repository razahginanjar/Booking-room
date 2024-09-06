package com.enigma.challengebookingroom.mapper.impl;

import com.enigma.challengebookingroom.mapper.UserMapper;
import com.enigma.challengebookingroom.dto.response.Auth.LoginResponse;
import com.enigma.challengebookingroom.dto.response.Auth.RegisterResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
