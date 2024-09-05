package com.enigma.challengebookingroom.Mapper.Impl;

import com.enigma.challengebookingroom.Mapper.EmployeeMapper;
import com.enigma.challengebookingroom.dto.response.Employee.EmployeeResponse;
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
