package com.enigma.challengebookingroom.controller;

import com.enigma.challengebookingroom.constant.APIUrl;
import com.enigma.challengebookingroom.dto.request.EmployeeRequest;
import com.enigma.challengebookingroom.dto.request.UpdateEmployeeRequest;
import com.enigma.challengebookingroom.dto.response.CommonResponse;
import com.enigma.challengebookingroom.dto.response.EmployeeResponse;
import com.enigma.challengebookingroom.entity.Employee;
import com.enigma.challengebookingroom.mapper.EmployeeMapper;
import com.enigma.challengebookingroom.service.EmployeeService;
import com.enigma.challengebookingroom.service.UserService;
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
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;
    private final UserService userService;
    private final EmployeeMapper employeeMapper;

//    kita bikinnya employee dari register
//    jadi yg dibawah ga guna lagi hehe
//    @PostMapping(
//            produces = MediaType.APPLICATION_JSON_VALUE,
//            consumes = MediaType.APPLICATION_JSON_VALUE
//    )
//    public ResponseEntity<CommonResponse<EmployeeResponse>> createEmployee(@Valid @RequestBody EmployeeRequest request) {
//        logger.debug("Request to create employee: {}", request);
//        EmployeeResponse create = employeeService.createAndGetResponse(request);
//        CommonResponse<EmployeeResponse> response = CommonResponse.<EmployeeResponse>builder()
//                .statusCode(HttpStatus.CREATED.value())
//                .message(HttpStatus.CREATED.getReasonPhrase()) // pesannya gini dulu, ntar ganti aja (sebenernya sama aja sih sama yg di constant message)
//                .data(create)
//                .build();
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<EmployeeResponse>>> getAllEmployee() {

        List<Employee> employees = employeeService.getAllEmployee();
        List<EmployeeResponse> list = employees.stream().map(
                employeeMapper::toResponse
        ).toList();
        CommonResponse<List<EmployeeResponse>> response = CommonResponse.<List<EmployeeResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase()) // pesannya gini dulu, ntar ganti aja (sebenernya sama aja sih sama yg di constant message)
                .data(list)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

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