package com.enigma.challengebookingroom.mapper.impl;

import com.enigma.challengebookingroom.mapper.EmployeeMapper;
import com.enigma.challengebookingroom.dto.response.EmployeeResponse;
import com.enigma.challengebookingroom.entity.Employee;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class EmployeeMapperImpl implements EmployeeMapper {
    @Override
    public EmployeeResponse toResponse(Employee employee) {
        return EmployeeResponse.builder()
                .employeeId(employee.getEmployeeId())
                .department(employee.getDepartment())
                .employeeName(employee.getEmployeeName())
                .build();
    }
}
