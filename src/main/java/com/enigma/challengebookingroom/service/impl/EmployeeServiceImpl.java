package com.enigma.challengebookingroom.service.impl;

import com.enigma.challengebookingroom.dto.request.UpdateEmployeeRequest;
import com.enigma.challengebookingroom.entity.User;
import com.enigma.challengebookingroom.mapper.EmployeeMapper;
import com.enigma.challengebookingroom.service.EmployeeService;
import com.enigma.challengebookingroom.dto.request.EmployeeRequest;
import com.enigma.challengebookingroom.dto.response.EmployeeResponse;
import com.enigma.challengebookingroom.entity.Employee;
import com.enigma.challengebookingroom.repository.EmployeeRepository;
import com.enigma.challengebookingroom.service.UserService;
import com.enigma.challengebookingroom.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    private final ValidationUtils validator;

    @Transactional(readOnly = true)
    @Override
    public Employee getById(String id) {
        return employeeRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase())
        );
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Employee createAndGet(EmployeeRequest employee) {
        validator.validate(employee);
        Employee build = Employee.builder()
                .department(employee.getDepartment())
                .employeeName(employee.getEmployeeName())
                .corporateEmail(employee.getCorporateEmail())
                .phoneNumber(employee.getPhoneNumber())
                .build();
        return employeeRepository.saveAndFlush(build);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Employee updateEmployee(UpdateEmployeeRequest employee) {
        validator.validate(employee);
        Employee byId = getById(employee.getEmployeeId());
        byId.setEmployeeName(employee.getEmployeeName());
        byId.setDepartment(employee.getDepartment());
        byId.setCorporateEmail(employee.getCorporateEmail());
        byId.setPhoneNumber(employee.getPhoneNumber());
        return employeeRepository.saveAndFlush(byId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeEmployee(String id) {
        Employee byId = getById(id);
        employeeRepository.delete(byId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Employee getByUsername(String employeeName) {
        return employeeRepository.findByEmployeeName(employeeName).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase())
        );
    }

    @Transactional(readOnly = true)
    @Override
    public EmployeeResponse getByIdResponse(String id) {
        Employee byId = getById(id);
        return employeeMapper.toResponse(byId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public EmployeeResponse createAndGetResponse(EmployeeRequest employee) {
        Employee andGet = createAndGet(employee);
        return employeeMapper.toResponse(andGet);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public EmployeeResponse updateEmployeeResponse(UpdateEmployeeRequest employee) {
        Employee employee1 = updateEmployee(employee);
        return employeeMapper.toResponse(employee1);
    }
}
