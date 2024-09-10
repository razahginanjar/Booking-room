package com.enigma.challengebookingroom.controller;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.enigma.challengebookingroom.dto.request.UpdateEmployeeRequest;
import com.enigma.challengebookingroom.dto.response.CommonResponse;
import com.enigma.challengebookingroom.dto.response.EmployeeResponse;
import com.enigma.challengebookingroom.entity.Employee;
import com.enigma.challengebookingroom.service.EmployeeService;

class EmployeeControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeControllerTest.class);

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<Employee>>> getAllEmployee() {

        List<Employee> employees = employeeService.getAllEmployee();
        CommonResponse<List<Employee>> response = CommonResponse.<List<Employee>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(employees)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @Test
    void testGetEmployeeById() {
        // Arrange
        String employeeId = "123";
        EmployeeResponse employeeResponse = EmployeeResponse.builder()
                .employeeId(employeeId)
                .employeeName("John Doe")
                .department("IT")
                .build();
        when(employeeService.getByIdResponse(employeeId)).thenReturn(employeeResponse);

        // Act
        ResponseEntity<CommonResponse<EmployeeResponse>> responseEntity = employeeController.getEmployeeById(employeeId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(employeeResponse, responseEntity.getBody().getData());
        verify(employeeService, times(1)).getByIdResponse(employeeId);
        logger.info("Test passed: testGetEmployeeById");
    }

    @Test
    void testUpdateEmployee() {
        // Arrange
        UpdateEmployeeRequest updateRequest = new UpdateEmployeeRequest(null, null, null, null, null);
        EmployeeResponse updatedEmployee = EmployeeResponse.builder()
                .employeeId("123")
                .employeeName("Jane Doe")
                .department("HR")
                .build();
        when(employeeService.updateEmployeeResponse(any(UpdateEmployeeRequest.class))).thenReturn(updatedEmployee);

        // Act
        ResponseEntity<CommonResponse<EmployeeResponse>> responseEntity = employeeController.updateEmployee(updateRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedEmployee, responseEntity.getBody().getData());
        verify(employeeService, times(1)).updateEmployeeResponse(updateRequest);
        logger.info("Test passed: testUpdateEmployee");
    }

    @Test
    void testDeleteEmployeeById() {
        // Arrange
        String employeeId = "123";
        doNothing().when(employeeService).removeEmployee(employeeId);

        // Act
        ResponseEntity<CommonResponse<String>> responseEntity = employeeController.deleteEmployeeById(employeeId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Removed employee with id: " + employeeId, responseEntity.getBody().getMessage());
        verify(employeeService, times(1)).removeEmployee(employeeId);
        logger.info("Test passed: testDeleteEmployeeById");
    }
}

