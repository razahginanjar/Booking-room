package com.enigma.challengebookingroom.controller;

import com.enigma.challengebookingroom.constant.APIUrl;
import com.enigma.challengebookingroom.dto.request.UpdateEmployeeRequest;
import com.enigma.challengebookingroom.dto.response.CommonResponse;
import com.enigma.challengebookingroom.dto.response.EmployeeResponse;
import com.enigma.challengebookingroom.entity.Employee;
import com.enigma.challengebookingroom.mapper.EmployeeMapper;
import com.enigma.challengebookingroom.service.EmployeeService;
import com.enigma.challengebookingroom.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.EMPLOYEE)
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Employee")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;
    private final UserService userService;
    private final EmployeeMapper employeeMapper;

    @Operation(
            description = "Get all employee (ADMIN PRIVILEGE)",
            summary = "Get all employee "
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<EmployeeResponse>>> getAllEmployee() {

        List<Employee> employees = employeeService.getAllEmployee();
        List<EmployeeResponse> list = employees.stream().map(
                employeeMapper::toResponse
        ).toList();
        CommonResponse<List<EmployeeResponse>> response = CommonResponse.<List<EmployeeResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(list)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            description = "Get specific employee (ADMIN PRIVILEGE)",
            summary = "Get specific employee "
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping(
            path = APIUrl.PATH_VAR_ID,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<EmployeeResponse>> getEmployeeById(@PathVariable String id) {
        EmployeeResponse employeeById = employeeService.getByIdResponse(id);
        CommonResponse<EmployeeResponse> response = CommonResponse.<EmployeeResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase()) // pesannya gini dulu, ntar ganti aja (sebenernya sama aja sih sama yg di constant message)
                .data(employeeById)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            description = "Update employee information (ADMIN PRIVILEGE)",
            summary = "Update employee information"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<EmployeeResponse>> updateEmployee(@Valid @RequestBody UpdateEmployeeRequest request) {
        EmployeeResponse update = employeeService.updateEmployeeResponse(request);
        CommonResponse<EmployeeResponse> response = CommonResponse.<EmployeeResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase()) // pesannya gini dulu, ntar ganti aja (sebenernya sama aja sih sama yg di constant message)
                .data(update)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            description = "Delete specific employee (ADMIN PRIVILEGE)",
            summary = "Delete specific employee"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping(
            path =APIUrl.DELETE_ACCOUNT + APIUrl.PATH_VAR_ID,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> deleteEmployeeById(@PathVariable String id) {
        userService.remove(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Removed user with id: " + id)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}