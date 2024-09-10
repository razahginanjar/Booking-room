package com.enigma.challengebookingroom.controller;

import com.enigma.challengebookingroom.dto.request.EquipmentRequest;
import com.enigma.challengebookingroom.dto.response.EquipmentResponse;
import com.enigma.challengebookingroom.repository.EquipmentRepository;
import com.enigma.challengebookingroom.service.EquipmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class EquipmentTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    EquipmentService equipmentService;

    @Autowired
    EquipmentRepository equipmentRepository;

    private EquipmentRequest equipmentRequest;
    private EquipmentResponse equipmentResponse;

    @BeforeEach
    void setUp()
    {
        equipmentRepository.deleteAll();

        equipmentRequest = EquipmentRequest.builder()
                .idEquipment("1")
                .equipmentName("Projector")
                .build();

        equipmentResponse = EquipmentResponse.builder()
                .equipmentId("1")
                .equipmentName("Projector")
                .build();
    }

    @Test
    void createSuccess() throws Exception
    {
        when(equipmentService.createResponse(any(EquipmentRequest.class))).thenReturn(equipmentResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/equipments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(equipmentRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.equipmentId").value(equipmentResponse.getEquipmentId()))
                .andExpect(jsonPath("$.data.equipmentName").value(equipmentResponse.getEquipmentName()));

        verify(equipmentService, times(1)).createResponse(any(EquipmentRequest.class));
        log.info("Create equipment test passed");


    }

    @Test
    void createFailedNullRequest() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/equipments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());

        verify(equipmentService, times(0)).createResponse(any(EquipmentRequest.class));
        log.info("Create equipment failed test (null request) passed");

    }

    @Test
    void getByIdSuccess() throws Exception
    {
        when(equipmentService.getResponseById(anyString())).thenReturn(equipmentResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/equipments/{id}", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.equipmentId").value(equipmentResponse.getEquipmentId()))
                .andExpect(jsonPath("$.data.equipmentName").value(equipmentResponse.getEquipmentName()));

        verify(equipmentService, times(1)).getResponseById(anyString());
        log.info("Get equipment by ID test passed");

    }

    @Test
    void getByIdFailed() throws Exception
    {
        when(equipmentService.getResponseById(anyString())).thenThrow(new RuntimeException("Not Found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/equipments/{id}", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(equipmentService, times(1)).getResponseById(anyString());
        log.warn("Get equipment by ID failed test passed");

    }

    @Test
    void getAllTest() throws Exception
    {
        when(equipmentService.getAll()).thenReturn(List.of(equipmentResponse));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/equipments")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].equipmentId").value(equipmentResponse.getEquipmentId()))
                .andExpect(jsonPath("$.data[0].equipmentName").value(equipmentResponse.getEquipmentName()));

        verify(equipmentService, times(1)).getAll();
        log.info("Get all equipment test passed");

    }

    @Test
    void updateSuccess() throws Exception
    {
        when(equipmentService.updateResponse(any(EquipmentRequest.class))).thenReturn(equipmentResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/equipments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(equipmentRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.equipmentId").value(equipmentResponse.getEquipmentId()))
                .andExpect(jsonPath("$.data.equipmentName").value(equipmentResponse.getEquipmentName()));

        verify(equipmentService, times(1)).updateResponse(any(EquipmentRequest.class));
        log.info("Update equipment test passed");

    }

    @Test
    void updateFailedRequestNull() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/equipments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());

        verify(equipmentService, times(0)).updateResponse(any(EquipmentRequest.class));
        log.warn("Update equipment failed test (null request) passed");

    }

    @Test
    void deleteSuccess() throws Exception
    {
        doNothing().when(equipmentService).deleteById(anyString());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/equipments/{id}", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(equipmentService, times(1)).deleteById(anyString());
        log.info("Delete equipment test passed");

    }

    @Test
    void deleteFailedNotFound() throws Exception
    {
        doThrow(new RuntimeException("Not Found")).when(equipmentService).deleteById(anyString());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/equipments/{id}", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(equipmentService, times(1)).deleteById(anyString());
        log.warn("Delete equipment failed test (not found) passed");

    }
}
