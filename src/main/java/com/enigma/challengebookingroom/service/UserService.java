package com.enigma.challengebookingroom.service;

import com.enigma.challengebookingroom.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User getById(String id);

    void remove(String id);

    User getByContext();
}
