package com.enigma.challengebookingroom.controller;

import com.enigma.challengebookingroom.constant.APIUrl;
import com.enigma.challengebookingroom.dto.request.AddRoleRequest;
import com.enigma.challengebookingroom.dto.request.LoginRequest;
import com.enigma.challengebookingroom.dto.request.RegisterRequest;
import com.enigma.challengebookingroom.dto.response.Auth.LoginResponse;
import com.enigma.challengebookingroom.dto.response.Auth.RegisterResponse;
import com.enigma.challengebookingroom.dto.response.CommonResponse;
import com.enigma.challengebookingroom.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.AUTH)
@Tag(name = "Authentication")
public class AuthController {
    private final AuthService authService;

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @Operation(
            description = "Register new user (ADMIN PRIVILEGE)",
            summary = "Register new user"
    )
    @PostMapping(
            path = APIUrl.PATH_REGISTER,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<?>> register(@RequestBody RegisterRequest request) {
        RegisterResponse register = authService.register(request);
        CommonResponse<RegisterResponse> response = CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .data(register)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            description = "Login",
            summary = "Login"
    )
    @PostMapping(
            path = APIUrl.PATH_LOGIN,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<?>> login(@RequestBody LoginRequest request) {
        LoginResponse login = authService.login(request);
        CommonResponse<LoginResponse> response = CommonResponse.<LoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase() + ". Login success")
                .data(login)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping(
            path = "/roles",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<RegisterResponse>> addRole(@RequestBody AddRoleRequest request) {
        RegisterResponse role = authService.addRole(request);
        CommonResponse<RegisterResponse> response = CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase() + ". Add role success")
                .data(role)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
