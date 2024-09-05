package com.enigma.challengebookingroom.service.impl;

import com.enigma.challengebookingroom.constant.ConstantRole;
import com.enigma.challengebookingroom.dto.request.RoleRequest;
import com.enigma.challengebookingroom.entity.Role;
import com.enigma.challengebookingroom.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate_ShouldReturnSavedRole() {
        // Arrange
        RoleRequest request = new RoleRequest("1", ConstantRole.ADMINISTRATOR);

        Role role = Role.builder()
                .constantRole(ConstantRole.ADMINISTRATOR)
                .build();

        when(roleRepository.saveAndFlush(any(Role.class))).thenReturn(role);

        // Act
        Role savedRole = roleService.create(request);

        // Assert
        assertNotNull(savedRole);
        assertEquals(ConstantRole.ADMINISTRATOR, savedRole.getConstantRole());
        verify(roleRepository, times(1)).saveAndFlush(any(Role.class));
    }

    @Test
    void testGetById_ShouldReturnRole_WhenFound() {
        // Arrange
        String roleId = "1";
        Role role = new Role();
        role.setRoleId(roleId);
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        // Act
        Role foundRole = roleService.getById(roleId);

        // Assert
        assertNotNull(foundRole);
        assertEquals(roleId, foundRole.getRoleId());
        verify(roleRepository, times(1)).findById(roleId);
    }

    @Test
    void testGetById_ShouldThrowException_WhenNotFound() {
        // Arrange
        String roleId = "1";
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> roleService.getById(roleId));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Role is not found", exception.getReason());
        verify(roleRepository, times(1)).findById(roleId);
    }

    @Test
    void testUpdate_ShouldReturnUpdatedRole() {
        // Arrange
        String roleId = "1";
        RoleRequest request = new RoleRequest(roleId, ConstantRole.SUPERVISOR);

        Role existingRole = new Role();
        existingRole.setRoleId(roleId);
        existingRole.setConstantRole(ConstantRole.ADMINISTRATOR);

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(existingRole));
        when(roleRepository.saveAndFlush(existingRole)).thenReturn(existingRole);

        // Act
        Role updatedRole = roleService.update(request);

        // Assert
        assertEquals(ConstantRole.SUPERVISOR, updatedRole.getConstantRole());
        verify(roleRepository, times(1)).findById(roleId);
        verify(roleRepository, times(1)).saveAndFlush(existingRole);
    }

    @Test
    void testDelete_ShouldCallRepositoryDelete_WhenRoleExists() {
        // Arrange
        String roleId = "1";
        Role role = new Role();
        role.setRoleId(roleId);
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        // Act
        roleService.delete(roleId);

        // Assert
        verify(roleRepository, times(1)).saveAndFlush(role);
    }

    @Test
    void testDelete_ShouldThrowException_WhenRoleNotFound() {
        // Arrange
        String roleId = "1";
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> roleService.delete(roleId));
        verify(roleRepository, times(1)).findById(roleId);
    }
}
