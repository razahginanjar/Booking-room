package com.enigma.challengebookingroom.service.impl;

import com.enigma.challengebookingroom.service.RoleService;
import com.enigma.challengebookingroom.dto.request.RoleRequest;
import com.enigma.challengebookingroom.entity.Role;
import com.enigma.challengebookingroom.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role create(RoleRequest request) {
        Role role = Role.builder()
                .constantRole(request.getConstantRole())
                .build();
        return roleRepository.saveAndFlush(role);
    }

    @Override
    public Role getById(String id) {
        return roleRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role is not found")
        );
    }

    @Override
    public Role update(RoleRequest request) {
        Role byId = getById(request.getRoleId());
        byId.setConstantRole(request.getConstantRole());
        return roleRepository.saveAndFlush(byId);
    }

    @Override
    public void delete(String id) {
        Role byId = getById(id);
        roleRepository.saveAndFlush(byId);
    }
}
