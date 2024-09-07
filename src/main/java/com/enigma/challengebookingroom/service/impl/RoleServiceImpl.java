package com.enigma.challengebookingroom.service.impl;

import com.enigma.challengebookingroom.constant.ConstantRole;
import com.enigma.challengebookingroom.service.RoleService;
import com.enigma.challengebookingroom.dto.request.RoleRequest;
import com.enigma.challengebookingroom.entity.Role;
import com.enigma.challengebookingroom.repository.RoleRepository;
import com.enigma.challengebookingroom.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ValidationUtils validator;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Role create(RoleRequest request) {
        validator.validate(request);
        if(roleRepository.existsByConstantRole(request.getConstantRole()))
        {
            return roleRepository.findByConstantRole(request.getConstantRole()).orElse(null);
        }
        Role role = Role.builder()
                .constantRole(request.getConstantRole())
                .build();
        return roleRepository.saveAndFlush(role);
    }

    @Transactional(readOnly = true)
    @Override
    public Role getById(String id) {
        return roleRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase())
        );
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Role update(RoleRequest request) {
        validator.validate(request);
        Role byId = getById(request.getRoleId());
        byId.setConstantRole(request.getConstantRole());
        return roleRepository.saveAndFlush(byId);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        Role byId = getById(id);
        roleRepository.saveAndFlush(byId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Role getOrSave(ConstantRole role) {
        return roleRepository.findByConstantRole(role)
                .orElseGet(() -> roleRepository.saveAndFlush(
                        Role.builder().constantRole(role).build()
                ));
    }
}
