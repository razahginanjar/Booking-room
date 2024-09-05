package com.enigma.challengebookingroom.service;

import com.enigma.challengebookingroom.entity.User;

public interface UserService {
    User getById(String id);
    void remove(String id);
}
