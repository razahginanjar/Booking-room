package com.enigma.challengebookingroom.service.impl;

import com.enigma.challengebookingroom.mapper.EmployeeMapper;
import com.enigma.challengebookingroom.service.EmployeeService;
import com.enigma.challengebookingroom.dto.request.EmployeeRequest;
import com.enigma.challengebookingroom.dto.response.EmployeeResponse;
import com.enigma.challengebookingroom.entity.Employee;
import com.enigma.challengebookingroom.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    @Override
    public Employee getById(String id) {
        return employeeRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee is not found")
        );
    }

    @Override
    public Employee createAndGet(EmployeeRequest employee) {
        Employee build = Employee.builder()
                .department(employee.getDepartment())
                .employeeName(employee.getEmployeeName())
//                .user()
                .corporateEmail(employee.getCorporateEmail())
                .phoneNumber(employee.getPhoneNumber())
                .build();
        return employeeRepository.saveAndFlush(build);
    }

    @Override
    public Employee updateEmployee(EmployeeRequest employee) {
        Employee byId = getById(employee.getEmployeeId());
        byId.setEmployeeName(employee.getEmployeeName());
        byId.setDepartment(employee.getDepartment());
        byId.setCorporateEmail(employee.getCorporateEmail());
        byId.setPhoneNumber(employee.getPhoneNumber());
        return employeeRepository.saveAndFlush(byId);
    }

    @Override
    public void RemoveEmployee(String id) {
        Employee byId = getById(id);
        employeeRepository.delete(byId);
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getByUsername(String employeeName) {
        return employeeRepository.findByEmployeeName(employeeName).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee is not found")
        );
    }

    @Override
    public EmployeeResponse getByIdResponse(String id) {
        Employee byId = getById(id);
        return employeeMapper.toResponse(byId);
    }

    @Override
    public EmployeeResponse createAndGetResponse(EmployeeRequest employee) {
        Employee andGet = createAndGet(employee);
        return employeeMapper.toResponse(andGet);
    }

    @Override
    public EmployeeResponse updateEmployeeResponse(EmployeeRequest employee) {
        Employee employee1 = updateEmployee(employee);
        return employeeMapper.toResponse(employee1);
    }
}
