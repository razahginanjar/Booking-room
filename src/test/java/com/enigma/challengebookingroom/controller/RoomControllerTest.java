package com.enigma.challengebookingroom.controller;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.enigma.challengebookingroom.dto.response.CommonResponse;
import com.enigma.challengebookingroom.dto.response.RoomResponse;
import com.enigma.challengebookingroom.entity.Room;
import com.enigma.challengebookingroom.service.RoomService;

class RoomControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(RoomControllerTest.class);

    @Mock
    private RoomService roomService;

    @InjectMocks
    private RoomController roomController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//     @Test
//     void testCreateRoom() {
//         // Arrange
//         RoomRequest roomRequest = RoomRequest.builder()
//                 .roomId("1")
//                 .roomType("Deluxe")
//                 .roomCapacity(2)
//                 .isAvailable(true)
//                 .build();

//         RoomResponse roomResponse = RoomResponse.builder()
//                 .roomId("1")
//                 .roomType("Deluxe")
//                 .roomCapacity(2)
//                 .roomFacilities(Collections.emptyList())
//                 .build();

//         CommonResponse<RoomResponse> expectedResponse = CommonResponse.<RoomResponse>builder()
//                 .statusCode(HttpStatus.CREATED.value())
//                 .message(HttpStatus.CREATED.getReasonPhrase())
//                 .data(roomResponse)
//                 .build();

//         doReturn(roomResponse).when(roomService).createAndGetResponse(any(RoomRequest.class));

//         // Act
//         ResponseEntity<CommonResponse<RoomResponse>> responseEntity = roomController.createRoom(roomRequest);

//         // Assert
//         assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//         assertEquals(expectedResponse, responseEntity.getBody());
//         logger.info("testCreateRoom executed successfully.");
//     }

    @Test
    void testGetAllRooms() {
        // Arrange
        Room room = Room.builder()
                .roomId("1")
                .roomType("Deluxe")
                .roomCapacity(2)
                .build();

        List<Room> roomList = Collections.singletonList(room);

        doReturn(roomList).when(roomService).getAll();

        // Act
        ResponseEntity<CommonResponse<List<Room>>> responseEntity = roomController.getAllRoom();

        // Assert
        CommonResponse<List<Room>> expectedResponse = CommonResponse.<List<Room>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Okay")
                .data(roomList)
                .build();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
        logger.info("testGetAllRooms executed successfully.");
    }

    @Test
    void testGetRoomById() {
        // Arrange
        String roomId = "1";
        RoomResponse roomResponse = RoomResponse.builder()
                .roomId(roomId)
                .roomType("Deluxe")
                .roomCapacity(2)
                .roomFacilities(Collections.emptyList())
                .build();

        CommonResponse<RoomResponse> expectedResponse = CommonResponse.<RoomResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(roomResponse)
                .build();

        doReturn(roomResponse).when(roomService).getByIdResponse(roomId);

        // Act
        ResponseEntity<CommonResponse<RoomResponse>> responseEntity = roomController.getRoomById(roomId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
        logger.info("testGetRoomById executed successfully.");
    }

//     @Test
//     void testUpdateRoom() {
//         // Arrange
//         RoomRequest roomRequest = RoomRequest.builder()
//                 .roomId("1")
//                 .roomType("Suite")
//                 .roomCapacity(3)
//                 .isAvailable(true)
//                 .build();

//         RoomResponse roomResponse = RoomResponse.builder()
//                 .roomId("1")
//                 .roomType("Suite")
//                 .roomCapacity(3)
//                 .roomFacilities(Collections.emptyList())
//                 .build();

//         CommonResponse<RoomResponse> expectedResponse = CommonResponse.<RoomResponse>builder()
//                 .statusCode(HttpStatus.OK.value())
//                 .message(HttpStatus.OK.getReasonPhrase())
//                 .data(roomResponse)
//                 .build();

//         doReturn(roomResponse).when(roomService).updateAndGetResponse(any(RoomRequest.class));

//         // Act
//         ResponseEntity<CommonResponse<RoomResponse>> responseEntity = roomController.updateRoom(roomRequest);

//         // Assert
//         assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//         assertEquals(expectedResponse, responseEntity.getBody());
//         logger.info("testUpdateRoom executed successfully.");
//     }

    @Test
    void testDeleteRoomById() {
        // Arrange
        String roomId = "1";
        CommonResponse<String> expectedResponse = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Deleted room with id: " + roomId)
                .data(null) // Handle null data properly
                .build();

        // Act
        ResponseEntity<CommonResponse<String>> responseEntity = roomController.deleteRoomById(roomId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
        verify(roomService).deleteRoom(roomId);
        logger.info("testDeleteRoomById executed successfully.");
    }
}

