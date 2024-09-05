package com.enigma.challengebookingroom.service;

import com.enigma.challengebookingroom.dto.request.EmployeeRequest;
import com.enigma.challengebookingroom.dto.response.EmployeeResponse;
import com.enigma.challengebookingroom.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee getById(String id);
    Employee createAndGet(EmployeeRequest employee);
    Employee updateEmployee(EmployeeRequest employee);
    void RemoveEmployee(String id);
    List<Employee> getAllEmployee();
    Employee getByUsername(String employeeName);
    EmployeeResponse getByIdResponse(String id);
    EmployeeResponse createAndGetResponse(EmployeeRequest employee);
    EmployeeResponse updateEmployeeResponse(EmployeeRequest employee);
}
