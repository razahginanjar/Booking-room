package com.enigma.challengebookingroom.mapper;

import com.enigma.challengebookingroom.dto.response.EmployeeResponse;
import com.enigma.challengebookingroom.entity.Employee;

public interface EmployeeMapper {
    EmployeeResponse toResponse(Employee employee);
}
