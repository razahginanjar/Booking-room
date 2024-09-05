package com.enigma.challengebookingroom.controller;

import com.enigma.challengebookingroom.constant.APIUrl;
import com.enigma.challengebookingroom.dto.request.EmployeeRequest;
import com.enigma.challengebookingroom.dto.response.CommonResponse;
import com.enigma.challengebookingroom.dto.response.EmployeeResponse;
import com.enigma.challengebookingroom.entity.Employee;
import com.enigma.challengebookingroom.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEmployee_ShouldReturnCreatedEmployee() {
        // Arrange
        EmployeeRequest request = new EmployeeRequest("1", "John Doe", "IT", "john.doe@example.com", "123456789");
        EmployeeResponse employeeResponse = new EmployeeResponse();

        when(employeeService.createAndGetResponse(request)).thenReturn(employeeResponse);

        // Act
        ResponseEntity<CommonResponse<EmployeeResponse>> responseEntity = employeeController.createEmployee(request);

        // Assert
        CommonResponse<EmployeeResponse> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        assertEquals(employeeResponse, response.getData());
        verify(employeeService, times(1)).createAndGetResponse(request);
    }

    @Test
    void testGetEmployeeById_ShouldReturnEmployee() {
        // Arrange
        String employeeId = "1";
        EmployeeResponse employeeResponse = new EmployeeResponse();

        when(employeeService.getByIdResponse(employeeId)).thenReturn(employeeResponse);

        // Act
        ResponseEntity<CommonResponse<EmployeeResponse>> responseEntity = employeeController.getRoomById(employeeId);

        // Assert
        CommonResponse<EmployeeResponse> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(employeeResponse, response.getData());
        verify(employeeService, times(1)).getByIdResponse(employeeId);
    }

    @Test
    void testUpdateEmployee_ShouldReturnUpdatedEmployee() {
        // Arrange
        EmployeeRequest request = new EmployeeRequest("1", "John Doe Updated", "HR", "john.updated@example.com", "987654321");
        EmployeeResponse updatedEmployeeResponse = new EmployeeResponse();

        when(employeeService.updateEmployeeResponse(request)).thenReturn(updatedEmployeeResponse);

        // Act
        ResponseEntity<CommonResponse<EmployeeResponse>> responseEntity = employeeController.updateRoom(request);

        // Assert
        CommonResponse<EmployeeResponse> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(updatedEmployeeResponse, response.getData());
        verify(employeeService, times(1)).updateEmployeeResponse(request);
    }

    @Test
    void testDeleteEmployee_ShouldReturnSuccessMessage() {
        // Arrange
        String employeeId = "1";
        String successMessage = "Removed employee with id: " + employeeId;

        // Act
        ResponseEntity<CommonResponse<String>> responseEntity = employeeController.deleteRoomById(employeeId);

        // Assert
        CommonResponse<String> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(successMessage, response.getMessage());
        verify(employeeService, times(1)).removeEmployee(employeeId);
    }
}
