package com.enigma.challengebookingroom.Service;

import com.enigma.challengebookingroom.dto.response.Auth.LoginResponse;
import com.enigma.challengebookingroom.dto.response.Auth.RegisterResponse;
import com.enigma.challengebookingroom.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    User getById(String id);
    void remove(String id);
}
