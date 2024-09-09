package com.enigma.challengebookingroom.controller;

import com.enigma.challengebookingroom.constant.APIUrl;
import com.enigma.challengebookingroom.dto.request.RoomRequest;
import com.enigma.challengebookingroom.dto.response.CommonResponse;
import com.enigma.challengebookingroom.dto.response.RoomResponse;
import com.enigma.challengebookingroom.entity.Room;
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


import java.util.List;
import java.util.UUID;

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
    void createdSuccess() throws Exception {
        RoomRequest request = new RoomRequest();
        request.setRoomCapacity(100);
        request.setRoomType("B.1");

        mockMvc.perform(
                post(APIUrl.ROOM)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isCreated()
        ).andDo(
                result -> {
                    CommonResponse<RoomResponse> commonResponseResponseEntity = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                    log.info(commonResponseResponseEntity.getMessage());
                }
        );
    }

    @Test
    void getByIdFailed() throws Exception {
        Room room = new Room();
        room.setRoomId(UUID.randomUUID().toString());
        room.setRoomFacilities(null);
        room.setRoomCapacity(100);
        room.setRoomType("b.1");

        roomRepository.saveAndFlush(room);

        mockMvc.perform(
                get(APIUrl.ROOM + "/dshvfyosafgasbfjab")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isNotFound()
        ).andDo(
                result -> {
                    CommonResponse<?> commonResponseResponseEntity = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                    log.info(commonResponseResponseEntity.getMessage());
                }
        );
    }

    @Test
    void getByIdSuccess() throws Exception {
        Room room = new Room();
        room.setRoomId(UUID.randomUUID().toString());
        room.setRoomFacilities(null);
        room.setRoomCapacity(100);
        room.setRoomType("b.1");

        Room room1 = roomRepository.saveAndFlush(room);
        System.out.println(APIUrl.ROOM + "/"+room1.getRoomId());
        mockMvc.perform(
                get(APIUrl.ROOM + "/"+room1.getRoomId())
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andDo(
                result -> {
//                    ResponseEntity<CommonResponse<RoomResponse>> commonResponseResponseEntity =
//                            objectMapper.readValue(result.getResponse().getContentAsString(),
//                            new TypeReference<>() {
//                            });
//                    log.info(commonResponseResponseEntity.getStatusCode().toString());
                }
        );
    }


    @Test
    void getAllSuccess() throws Exception {
        Room room = new Room();
        room.setRoomId(UUID.randomUUID().toString());
        room.setRoomFacilities(null);
        room.setRoomCapacity(100);
        room.setRoomType("b.1");

        Room room1 = roomRepository.saveAndFlush(room);
        mockMvc.perform(
                get(APIUrl.ROOM )
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andDo(
                result -> {
                    CommonResponse<List<RoomResponse>> commonResponseResponseEntity = objectMapper.readValue(result.getResponse().getContentAsString(), new
                            TypeReference<CommonResponse<List<RoomResponse>>>() {
                            });
                    log.info(commonResponseResponseEntity.getMessage());
                }
        );
    }

    @Test
    void updateFailedRequestNull() throws Exception {
        Room room = new Room();
        room.setRoomId(UUID.randomUUID().toString());
        room.setRoomFacilities(null);
        room.setRoomCapacity(100);
        room.setRoomType("b.1");

        Room room1 = roomRepository.saveAndFlush(room);
        RoomRequest request = new RoomRequest();
        request.setRoomCapacity(100);
        request.setRoomType("B.1");
        request.setRoomId("fgyaduiaydgaiugduabhs");

        mockMvc.perform(
                put(APIUrl.ROOM)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isNotFound()
        ).andDo(
                result -> {
                    CommonResponse<?> commonResponseResponseEntity = objectMapper.readValue(result.getResponse().getContentAsString(), new
                            TypeReference<>() {
                            });
                    log.info(commonResponseResponseEntity.getMessage());
                }
        );
    }

    @Test
    void updateSuccess() throws Exception {
        Room room = new Room();
        room.setRoomId(UUID.randomUUID().toString());
        room.setRoomFacilities(null);
        room.setRoomCapacity(100);
        room.setRoomType("b.1");

        Room room1 = roomRepository.saveAndFlush(room);

        RoomRequest request = new RoomRequest();
        request.setRoomCapacity(100);
        request.setRoomType("B.1");
        request.setRoomId(room1.getRoomId());

        mockMvc.perform(
                put(APIUrl.ROOM)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isOk()
        ).andDo(
                result -> {
                    CommonResponse<?> commonResponseResponseEntity = objectMapper.readValue(result.getResponse().getContentAsString(), new
                            TypeReference<CommonResponse<?>>() {
                            });
                    log.info(commonResponseResponseEntity.getMessage());
                }
        );
    }

    @Test
    void deleteFailed() throws Exception {
        Room room = new Room();
        room.setRoomId(UUID.randomUUID().toString());
        room.setRoomFacilities(null);
        room.setRoomCapacity(100);
        room.setRoomType("b.1");

        Room room1 = roomRepository.saveAndFlush(room);

        RoomRequest request = new RoomRequest();
        request.setRoomCapacity(100);
        request.setRoomType("B.1");
        request.setRoomId(room1.getRoomId());

        mockMvc.perform(
                delete(APIUrl.ROOM+"/"+"dkjnsui0bfgusbfhusdhfu")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isNotFound()
        ).andDo(
                result -> {
                    CommonResponse<?> commonResponseResponseEntity = objectMapper.readValue(result.getResponse().getContentAsString(), new
                            TypeReference<>() {
                            });
                    log.info(commonResponseResponseEntity.getMessage());
                }
        );
    }

    @Test
    void deleteSuccess() throws Exception {
        Room room = new Room();
        room.setRoomId(UUID.randomUUID().toString());
        room.setRoomFacilities(null);
        room.setRoomCapacity(100);
        room.setRoomType("b.1");

        Room room1 = roomRepository.saveAndFlush(room);

        RoomRequest request = new RoomRequest();
        request.setRoomCapacity(100);
        request.setRoomType("B.1");
        request.setRoomId(room1.getRoomId());

        mockMvc.perform(
                delete(APIUrl.ROOM+"/"+room1.getRoomId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isOk()
        ).andDo(
                result -> {
                    CommonResponse<?> commonResponseResponseEntity = objectMapper.readValue(result.getResponse().getContentAsString(), new
                            TypeReference<>() {
                            });
                    log.info(commonResponseResponseEntity.getMessage());
                }
        );
    }
}
