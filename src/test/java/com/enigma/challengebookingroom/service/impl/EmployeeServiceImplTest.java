package com.enigma.challengebookingroom.service.impl;

import com.enigma.challengebookingroom.dto.request.EmployeeRequest;
import com.enigma.challengebookingroom.entity.Employee;
import com.enigma.challengebookingroom.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById_ShouldReturnEmployee_WhenFound() {
        // Arrange
        String employeeId = "1";
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        // Act
        Employee foundEmployee = employeeService.getById(employeeId);

        // Assert
        assertEquals(employeeId, foundEmployee.getEmployeeId());
        verify(employeeRepository, times(1)).findById(employeeId);
    }

    @Test
    void testGetById_ShouldThrowException_WhenNotFound() {
        // Arrange
        String employeeId = "1";
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> employeeService.getById(employeeId));
        verify(employeeRepository, times(1)).findById(employeeId);
    }

    @Test
    void testCreateAndGet_ShouldReturnSavedEmployee() {
        // Arrange
        EmployeeRequest request = new EmployeeRequest("1", "John Doe", "IT", "john.doe@example.com", "123456789");

        Employee employee = Employee.builder()
                .employeeId("1")
                .employeeName("John Doe")
                .department("IT")
                .corporateEmail("john.doe@example.com")
                .phoneNumber("123456789")
                .build();

        when(employeeRepository.saveAndFlush(any(Employee.class))).thenReturn(employee);

        // Act
        Employee savedEmployee = employeeService.createAndGet(request);

        // Assert
        assertEquals("John Doe", savedEmployee.getEmployeeName());
        verify(employeeRepository, times(1)).saveAndFlush(any(Employee.class));
    }

    @Test
    void testUpdateEmployee_ShouldUpdateAndReturnEmployee() {
        // Arrange
        String employeeId = "1";
        EmployeeRequest request = new EmployeeRequest(employeeId, "John Doe Updated", "HR", "john.updated@example.com", "987654321");

        Employee existingEmployee = new Employee();
        existingEmployee.setEmployeeId(employeeId);
        existingEmployee.setEmployeeName("John Doe");
        existingEmployee.setDepartment("IT");
        existingEmployee.setCorporateEmail("john.doe@example.com");
        existingEmployee.setPhoneNumber("123456789");

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.saveAndFlush(any(Employee.class))).thenReturn(existingEmployee);

        // Act
        Employee updatedEmployee = employeeService.updateEmployee(request);

        // Assert
        assertEquals("John Doe Updated", updatedEmployee.getEmployeeName());
        assertEquals("HR", updatedEmployee.getDepartment());
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeRepository, times(1)).saveAndFlush(any(Employee.class));
    }

    @Test
    void testRemoveEmployee_ShouldDeleteEmployee_WhenFound() {
        // Arrange
        String employeeId = "1";
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        // Act
        employeeService.removeEmployee(employeeId);

        // Assert
        verify(employeeRepository, times(1)).delete(employee);
    }

    @Test
    void testGetAllEmployee_ShouldReturnListOfEmployees() {
        // Arrange
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee());
        when(employeeRepository.findAll()).thenReturn(employees);

        // Act
        List<Employee> result = employeeService.getAllEmployee();

        // Assert
        assertEquals(1, result.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testGetByUsername_ShouldReturnEmployee_WhenFound() {
        // Arrange
        String employeeName = "John Doe";
        Employee employee = new Employee();
        employee.setEmployeeName(employeeName);
        when(employeeRepository.findByEmployeeName(employeeName)).thenReturn(Optional.of(employee));

        // Act
        Employee foundEmployee = employeeService.getByUsername(employeeName);

        // Assert
        assertEquals(employeeName, foundEmployee.getEmployeeName());
        verify(employeeRepository, times(1)).findByEmployeeName(employeeName);
    }
}
