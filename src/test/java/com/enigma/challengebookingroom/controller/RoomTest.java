package com.enigma.challengebookingroom.controller;

import com.enigma.challengebookingroom.constant.APIUrl;
import com.enigma.challengebookingroom.dto.request.RoomRequest;
import com.enigma.challengebookingroom.dto.response.CommonResponse;
import com.enigma.challengebookingroom.dto.response.RoomResponse;
import com.enigma.challengebookingroom.repository.RoomRepository;
import com.enigma.challengebookingroom.service.impl.RoomServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class RoomTest {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp()
    {
        roomRepository.deleteAll();
    }

    @Test
    void createFailedNullRequest() throws Exception {
        RoomRequest request = new RoomRequest();

        mockMvc.perform(
                post(APIUrl.ROOM)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
            status().isBadRequest()
        ).andDo(
            result -> {
                Object o = objectMapper.readValue(result.getResponse().getContentAsString(),
                        new TypeReference<>() {
                        });
                log.info(o.toString());
            }
        );
    }

    @Test
    void createdSucces()
    {

    }

    @Test
    void getByIdFailed()
    {

    }

    @Test
    void getByIdSuccess()
    {

    }

    @Test
    void getAllSuccess()
    {

    }

    @Test
    void updateFailedRequestNull()
    {

    }

    @Test
    void updateSuccess()
    {

    }

    @Test
    void deleteFailed()
    {

    }

    @Test
    void deleteSuccess()
    {

    }
}
