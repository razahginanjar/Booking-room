package com.enigma.challengebookingroom.controller;

import com.enigma.challengebookingroom.repository.EquipmentRepository;
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
public class EquipmentTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EquipmentRepository equipmentRepository;

    @BeforeEach
    void setUp()
    {
        equipmentRepository.deleteAll();
    }

    @Test
    void createSuccess()
    {

    }

    @Test
    void createFailedNullRequest()
    {

    }

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
