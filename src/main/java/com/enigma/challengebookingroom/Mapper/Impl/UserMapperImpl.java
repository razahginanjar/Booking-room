package com.enigma.challengebookingroom.Mapper.Impl;

import com.enigma.challengebookingroom.Mapper.UserMapper;
import com.enigma.challengebookingroom.dto.response.Auth.LoginResponse;
import com.enigma.challengebookingroom.dto.response.Auth.RegisterResponse;
import org.springframework.stereotype.Service;

@Service
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
