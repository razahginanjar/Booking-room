package com.enigma.challengebookingroom.service.impl;

import com.enigma.challengebookingroom.entity.User;
import com.enigma.challengebookingroom.repository.UserRepository;
import com.enigma.challengebookingroom.service.EmployeeService;
import com.enigma.challengebookingroom.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById_ShouldReturnUser_WhenFound() {
        // Arrange
        String userId = "1";
        User user = User.builder()
                .userId(userId)
                .username("John Doe")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        User foundUser = userService.getById(userId);

        // Assert
        assertEquals(userId, foundUser.getUserId());
        assertEquals("John Doe", foundUser.getUsername());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetById_ShouldThrowException_WhenNotFound() {
        // Arrange
        String userId = "1";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> userService.getById(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testRemove_ShouldRemoveUser_WhenFound() {
        // Arrange
        String userId = "1";
        User user = User.builder()
                .userId(userId)
                .username("John Doe")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        userService.remove(userId);

        // Assert
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testRemove_ShouldThrowException_WhenNotFound() {
        // Arrange
        String userId = "1";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> userService.remove(userId));
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).delete(any(User.class));
    }
}
