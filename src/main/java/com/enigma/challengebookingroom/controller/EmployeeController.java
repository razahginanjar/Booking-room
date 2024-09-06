package com.enigma.challengebookingroom.controller;

import com.enigma.challengebookingroom.constant.APIUrl;
import com.enigma.challengebookingroom.dto.request.EmployeeRequest;
import com.enigma.challengebookingroom.dto.response.CommonResponse;
import com.enigma.challengebookingroom.dto.response.EmployeeResponse;
import com.enigma.challengebookingroom.entity.Employee;
import com.enigma.challengebookingroom.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ResourceBundle;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.EMPLOYEE)
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<EmployeeResponse>> createEmployee(@Valid @RequestBody EmployeeRequest request) {
        logger.debug("Request to create employee: {}", request);
        EmployeeResponse create = employeeService.createAndGetResponse(request);
        CommonResponse<EmployeeResponse> response = CommonResponse.<EmployeeResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase()) // pesannya gini dulu, ntar ganti aja (sebenernya sama aja sih sama yg di constant message)
                .data(create)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<Employee>> getAllRoom() {

        List<Employee> employees = employeeService.getAllEmployee();
        CommonResponse<Employee> response = CommonResponse.<Employee>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase()) // pesannya gini dulu, ntar ganti aja (sebenernya sama aja sih sama yg di constant message)
                .data((Employee) employees)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(
            path = APIUrl.PATH_VAR_ID,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<EmployeeResponse>> getRoomById(@PathVariable String id) {
        EmployeeResponse employeeById = employeeService.getByIdResponse(id);
        CommonResponse<EmployeeResponse> response = CommonResponse.<EmployeeResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase()) // pesannya gini dulu, ntar ganti aja (sebenernya sama aja sih sama yg di constant message)
                .data(employeeById)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<EmployeeResponse>> updateRoom(@Valid @RequestBody EmployeeRequest request) {
        EmployeeResponse update = employeeService.updateEmployeeResponse(request);
        CommonResponse<EmployeeResponse> response = CommonResponse.<EmployeeResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase()) // pesannya gini dulu, ntar ganti aja (sebenernya sama aja sih sama yg di constant message)
                .data(update)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(
            path =APIUrl.PATH_VAR_ID,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> deleteRoomById(@PathVariable String id) {
        employeeService.removeEmployee(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Removed employee with id: " + id)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}