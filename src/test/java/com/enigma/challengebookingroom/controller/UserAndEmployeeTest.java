package com.enigma.challengebookingroom.controller;

import com.enigma.challengebookingroom.constant.APIUrl;
import com.enigma.challengebookingroom.dto.request.LoginRequest;
import com.enigma.challengebookingroom.dto.request.RegisterRequest;
import com.enigma.challengebookingroom.dto.request.UpdateEmployeeRequest;
import com.enigma.challengebookingroom.dto.response.CommonResponse;
import com.enigma.challengebookingroom.dto.response.EmployeeResponse;
import com.enigma.challengebookingroom.entity.User;
import com.enigma.challengebookingroom.repository.EmployeeRepository;
import com.enigma.challengebookingroom.repository.UserRepository;
import com.enigma.challengebookingroom.service.AuthService;
import com.enigma.challengebookingroom.service.impl.EmployeeServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class UserAndEmployeeTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authService;

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeServiceImpl employeeServiceImpl;

    @BeforeEach
    void setUp()
    {
        userRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    //user or auth request
    @Test
    void RegisterSuccessTest() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                .corporateEmail("ajasbduandinas@gmail.com")
                .department("manager")
                .employeeName("manami")
                .password("1234666")
                .phoneNumber("0821322422")
                .username("rezasambat")
                .build();

        mockMvc.perform(
                post(APIUrl.AUTH + APIUrl.PATH_REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isCreated()
        ).andDo(
            result ->
            {
                CommonResponse<?> commonResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
                        new TypeReference<>() {
                        });
                log.info(commonResponse.getData().toString());
                //Assertions.assertEquals(commonResponse.);
            }
        );
    }


    @Test
    void RegisterFailedNullRequestTest() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
//                .corporateEmail("ajasbduandinas@gmail.com")
//                .department("manager")
//                .employeeName("manami")
//                .password("1234666")
//                .phoneNumber("0821322422")
//                .username("rezasambat")
                .build();

        mockMvc.perform(
                post(APIUrl.AUTH + APIUrl.PATH_REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isBadRequest()
        ).andDo(
                result ->
                {
                    CommonResponse<?> commonResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<CommonResponse<?>>() {
                            });
                    log.info(commonResponse.getData().toString());
                    //Assertions.assertEquals(commonResponse.);
                }
        );
    }

    @Test
    void LoginSuccessTest() throws Exception {

        RegisterRequest registerRequest = RegisterRequest.builder()
                .corporateEmail("ajasbduandinas@gmail.com")
                .department("manager")
                .employeeName("manami")
                .password("1234666")
                .phoneNumber("0821322422")
                .username("rezasambat")
                .build();

        authService.register(registerRequest);

        LoginRequest request = LoginRequest.builder()
                .password("1234666")
                .username("rezasambat")
                .build();

        mockMvc.perform(
                post(APIUrl.AUTH + APIUrl.PATH_LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andDo(
                result ->
                {
                    CommonResponse<?> commonResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<CommonResponse<?>>() {
                            });
                    log.info(commonResponse.getData().toString());
                    //Assertions.assertEquals(commonResponse.);
                }
        );
    }

    @Test
    void LoginFailedTest() throws Exception {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .corporateEmail("ajasbduandinas@gmail.com")
                .department("manager")
                .employeeName("manami")
                .password("1234666")
                .phoneNumber("0821322422")
                .username("rezasambat")
                .build();

        authService.register(registerRequest);

        LoginRequest request = LoginRequest.builder()
                .password("1234666")
                .username("rezasa")
                .build();

        mockMvc.perform(
                post(APIUrl.AUTH + APIUrl.PATH_LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isNotFound()
        ).andDo(
                result ->
                {
                    CommonResponse<?> commonResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<CommonResponse<?>>() {
                            });
                    log.info(commonResponse.getData().toString());
                    //Assertions.assertEquals(commonResponse.);
                }
        );
    }

    //employee test
    @Test
    void getByIdSuccess() throws Exception {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .corporateEmail("ajasbduandinas@gmail.com")
                .department("manager")
                .employeeName("manami")
                .password("1234666")
                .phoneNumber("0821322422")
                .username("rezasambat")
                .build();

        authService.register(registerRequest);

        User user = userRepository.findByUsername(registerRequest.getUsername()).orElse(null);
        assert user != null;
        mockMvc.perform(
                get(APIUrl.EMPLOYEE+ "/"+user.getEmployee().getEmployeeId())
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andDo(
                result ->
                {
                    CommonResponse<?> commonResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<CommonResponse<?>>() {
                            });
                    log.info(commonResponse.getData().toString());
                    //Assertions.assertEquals(commonResponse.);
                }
        );
    }

    @Test
    void getByIdFailed() throws Exception {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .corporateEmail("ajasbduandinas@gmail.com")
                .department("manager")
                .employeeName("manami")
                .password("1234666")
                .phoneNumber("0821322422")
                .username("rezasambat")
                .build();

        authService.register(registerRequest);

        User user = userRepository.findByUsername(registerRequest.getUsername()).orElse(null);
        assert user != null;
        mockMvc.perform(
                get(APIUrl.EMPLOYEE+ "/sdjgasyuglduiafstdfausfguiagsuia")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isNotFound()
        ).andDo(
                result ->
                {
                    CommonResponse<?> commonResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<CommonResponse<?>>() {
                            });
                    log.info(commonResponse.getData().toString());
                    //Assertions.assertEquals(commonResponse.);
                }
        );
    }

    @Test
    void getAllTest() throws Exception {
        for (int i = 0; i < 5; i++) {
            RegisterRequest registerRequest = RegisterRequest.builder()
                    .corporateEmail("ajasbduandinas@gmail.com" + i)
                    .department("manager")
                    .employeeName("manami" + i)
                    .password("1234666")
                    .phoneNumber("0821322422"+ i)
                    .username("rezasambat"+i)
                    .build();

            authService.register(registerRequest);
        }

        mockMvc.perform(
                get(APIUrl.EMPLOYEE)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andDo(
                result ->
                {
                    CommonResponse<List<EmployeeResponse>> commonResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                    log.info(commonResponse.getData().toString());
                    Assertions.assertEquals(commonResponse.getData().size(), 5);
                }
        );
    }

    @Test
    void updateSuccess() throws Exception {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .corporateEmail("ajasbduandinas@gmail.com")
                .department("manager")
                .employeeName("manami")
                .password("1234666")
                .phoneNumber("0821322422")
                .username("rezasambat")
                .build();

        authService.register(registerRequest);

        User user = userRepository.findByUsername(registerRequest.getUsername()).orElse(null);
        assert user != null;


        UpdateEmployeeRequest employeeRequest = UpdateEmployeeRequest
                .builder()
                .employeeId(user.getEmployee().getEmployeeId())
                .employeeName("nono")
                .corporateEmail("adjvbashdaj@gmail")
                .department("fisika")
                .phoneNumber("082362322")
                .build();

        mockMvc.perform(
                put(APIUrl.EMPLOYEE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeRequest))
        ).andExpect(
                status().isOk()
        ).andDo(
                result ->
                {
                    CommonResponse<EmployeeResponse> commonResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                    log.info(commonResponse.getData().getEmployeeName());

                    //Assertions.assertEquals(commonResponse.);
                }
        );
    }

    @Test
    void updateFailedRequestNull() throws Exception {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .corporateEmail("ajasbduandinas@gmail.com")
                .department("manager")
                .employeeName("manami")
                .password("1234666")
                .phoneNumber("0821322422")
                .username("rezasambat")
                .build();

        authService.register(registerRequest);

        User user = userRepository.findByUsername(registerRequest.getUsername()).orElse(null);
        assert user != null;


        UpdateEmployeeRequest employeeRequest = UpdateEmployeeRequest
                .builder()
                .build();

        mockMvc.perform(
                put(APIUrl.EMPLOYEE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeRequest))
        ).andExpect(
                status().isBadRequest()
        ).andDo(
                result ->
                {
                    CommonResponse<?> commonResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                    log.info(commonResponse.getData().toString());
                }
        );
    }

    //delete account
    @Test
    void deleteSuccess() throws Exception {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .corporateEmail("ajasbduandinas@gmail.com")
                .department("manager")
                .employeeName("manami")
                .password("1234666")
                .phoneNumber("0821322422")
                .username("rezasambat")
                .build();

        authService.register(registerRequest);

        User user = userRepository.findByUsername(registerRequest.getUsername()).orElse(null);
        assert user != null;

        mockMvc.perform(
                delete(APIUrl.EMPLOYEE+APIUrl.DELETE_ACCOUNT+"/"+user.getUserId())
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andDo(
                result ->
                {
                    CommonResponse<?> commonResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                    log.info(commonResponse.getMessage());
                }
        );
    }

    @Test
    void deleteFailedNotFound() throws Exception {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .corporateEmail("ajasbduandinas@gmail.com")
                .department("manager")
                .employeeName("manami")
                .password("1234666")
                .phoneNumber("0821322422")
                .username("rezasambat")
                .build();

        authService.register(registerRequest);

        User user = userRepository.findByUsername(registerRequest.getUsername()).orElse(null);
        assert user != null;

        mockMvc.perform(
                delete(APIUrl.EMPLOYEE+APIUrl.DELETE_ACCOUNT+"/asuihafuishifoahishj")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isNotFound()
        ).andDo(
                result ->
                {
                    CommonResponse<?> commonResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                    log.info(commonResponse.getMessage());
                }
        );
    }
}
