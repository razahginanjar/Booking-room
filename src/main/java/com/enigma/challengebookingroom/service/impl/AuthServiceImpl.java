package com.enigma.challengebookingroom.service.impl;

import com.enigma.challengebookingroom.constant.ConstantRole;
import com.enigma.challengebookingroom.dto.request.EmployeeRequest;
import com.enigma.challengebookingroom.dto.request.LoginRequest;
import com.enigma.challengebookingroom.dto.request.RegisterRequest;
import com.enigma.challengebookingroom.dto.request.RoleRequest;
import com.enigma.challengebookingroom.dto.response.Auth.LoginResponse;
import com.enigma.challengebookingroom.dto.response.Auth.RegisterResponse;
import com.enigma.challengebookingroom.dto.response.EmployeeResponse;
import com.enigma.challengebookingroom.entity.Employee;
import com.enigma.challengebookingroom.entity.Role;
import com.enigma.challengebookingroom.entity.User;
import com.enigma.challengebookingroom.repository.UserRepository;
import com.enigma.challengebookingroom.service.AuthService;
import com.enigma.challengebookingroom.service.EmployeeService;
import com.enigma.challengebookingroom.service.JwtService;
import com.enigma.challengebookingroom.service.RoleService;
import com.enigma.challengebookingroom.util.ValidationUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeService employeeService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ValidationUtils validation;

    @Value("${challengebookingroom.superadmin.username}")
    private String superAdminUsername;
    @Value("${challengebookingroom.superadmin.password}")
    private String superAdminPassword;

    @PostConstruct
    @Transactional(rollbackFor = Exception.class)
    public void init() {
        Optional<User> userSuperAdmin = userRepository.findByUsername(superAdminUsername);
        if (userSuperAdmin.isPresent()) return;


        Role admin = roleService.create(RoleRequest.builder().constantRole(ConstantRole.ROLE_ADMINISTRATOR).build());
        Role ga = roleService.create(RoleRequest.builder().constantRole(ConstantRole.ROLE_GENERAL_AFFAIR).build());
        Role user = roleService.create(RoleRequest.builder().constantRole(ConstantRole.ROLE_USER).build());

        User account = User.builder()
                .username(superAdminUsername)
                .password(passwordEncoder.encode(superAdminPassword))
                .roles(List.of(admin, ga, user))
                .build();
        userRepository.save(account);
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        validation.validate(request);
        Role role = roleService.getOrSave(ConstantRole.ROLE_USER);
        String hashPassword = passwordEncoder.encode(request.getPassword());

        EmployeeRequest employee = EmployeeRequest.builder()
                .employeeName(request.getEmployeeName())
                .department(request.getDepartment())
                .phoneNumber(request.getPhoneNumber())
                .corporateEmail(request.getCorporateEmail())
                .build();
        Employee response = employeeService.createAndGet(employee);

        EmployeeResponse employeeResponse = EmployeeResponse.builder()
                .employeeId(response.getEmployeeId())
                .employeeName(employee.getEmployeeName())
                .department(employee.getDepartment())
                .build();

        User user = User.builder()
                .username(request.getUsername())
                .employee(response)
                .password(hashPassword)
                .roles(List.of(role))
                .build();
        userRepository.saveAndFlush(user);

        return RegisterResponse.builder()
                .username(user.getUsername())
                .roles(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .employee(employeeResponse)
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) throws DataIntegrityViolationException {
        validation.validate(request);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        Authentication authenticated = authenticationManager.authenticate(authentication);
        User account = (User) authenticated.getPrincipal();
        String token = jwtService.generateToken(account);
        return LoginResponse.builder()
                .token(token)
                .username(account.getUsername())
                .roles(account.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }
}
