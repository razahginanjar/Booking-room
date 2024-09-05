package com.enigma.challengebookingroom.Mapper;

import com.enigma.challengebookingroom.dto.response.Employee.EmployeeResponse;
import com.enigma.challengebookingroom.entity.Employee;

public interface EmployeeMapper {
    EmployeeResponse toResponse(Employee employee);
}
