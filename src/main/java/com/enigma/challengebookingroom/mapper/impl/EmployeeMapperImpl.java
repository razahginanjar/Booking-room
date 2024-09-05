package com.enigma.challengebookingroom.mapper.impl;

import com.enigma.challengebookingroom.mapper.EmployeeMapper;
import com.enigma.challengebookingroom.dto.response.EmployeeResponse;
import com.enigma.challengebookingroom.entity.Employee;
import org.springframework.stereotype.Service;

@Service
public class EmployeeMapperImpl implements EmployeeMapper {
    @Override
    public EmployeeResponse toResponse(Employee employee) {
        return EmployeeResponse.builder()
                .department(employee.getEmployeeName())
                .employeeName(employee.getDepartment())
                .build();
    }
}
