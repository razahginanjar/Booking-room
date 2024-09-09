package com.enigma.challengebookingroom.controller;

import com.enigma.challengebookingroom.repository.EmployeeRepository;
import com.enigma.challengebookingroom.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

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
    EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp()
    {
        userRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    //user or auth request
    @Test
    void RegisterSuccessTest()
    {

    }


    @Test
    void RegisterFailedNullRequestTest()
    {

    }

    @Test
    void LoginSuccessTest()
    {

    }

    @Test
    void LoginFailedTest()
    {

    }

    //employee test
    @Test
    void getByIdSuccess()
    {

    }

    @Test
    void getByIdFailed()
    {

    }

    @Test
    void getAllTest()
    {

    }

    @Test
    void updateSuccess()
    {

    }

    @Test
    void updateFailedRequestNull()
    {

    }

    @Test
    void deleteSuccess()
    {

    }

    @Test
    void deleteFailedNotFound()
    {

    }
}
