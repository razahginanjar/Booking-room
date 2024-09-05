package com.enigma.challengebookingroom.service;

import com.enigma.challengebookingroom.dto.request.RoleRequest;
import com.enigma.challengebookingroom.entity.Role;

public interface RoleService {
    Role create(RoleRequest request);
    Role getById(String id);
    Role update(RoleRequest request);
    void delete(String id);
}
