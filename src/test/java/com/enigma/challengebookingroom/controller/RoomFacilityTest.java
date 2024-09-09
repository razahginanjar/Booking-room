package com.enigma.challengebookingroom.controller;

import com.enigma.challengebookingroom.repository.RoomFacilityRepository;
import com.enigma.challengebookingroom.repository.RoomRepository;
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
public class RoomFacilityTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RoomFacilityRepository roomFacilityRepository;

    @BeforeEach
    void setUp()
    {

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
