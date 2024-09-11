// package com.enigma.challengebookingroom.controller;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import static org.mockito.ArgumentMatchers.any;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import static org.mockito.Mockito.doReturn;
// import static org.mockito.Mockito.verify;
// import org.mockito.MockitoAnnotations;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;

// import com.enigma.challengebookingroom.dto.request.RoomFacilityRequest;
// import com.enigma.challengebookingroom.dto.response.CommonResponse;
// import com.enigma.challengebookingroom.dto.response.RoomFacilityResponse;
// import com.enigma.challengebookingroom.service.RoomFacilityService;

// class RoomFacilityControllerTest {

//     private static final Logger logger = LoggerFactory.getLogger(RoomFacilityControllerTest.class);

//     @Mock
//     private RoomFacilityService roomFacilityService;

//     @InjectMocks
//     private RoomFacilityController roomFacilityController;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     void testCreateRoomFacility() {
//         // Arrange
//         RoomFacilityRequest request = RoomFacilityRequest.builder()
//                 .roomFacilityId("1")
//                 .roomFacilityName("WiFi")
//                 .build();

//         RoomFacilityResponse response = RoomFacilityResponse.builder()
//                 .roomId("1")
//                 .roomFacilityName("WiFi")
//                 .build();

//         CommonResponse<RoomFacilityResponse> expectedResponse = CommonResponse.<RoomFacilityResponse>builder()
//                 .statusCode(HttpStatus.CREATED.value())
//                 .message(HttpStatus.CREATED.getReasonPhrase())
//                 .data(response)
//                 .build();

//         doReturn(response).when(roomFacilityService).createAndGetResponse(any(RoomFacilityRequest.class));

//         // Act
//         ResponseEntity<CommonResponse<RoomFacilityResponse>> responseEntity = roomFacilityController.createRoomFacility(request);

//         // Assert
//         assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//         assertEquals(expectedResponse, responseEntity.getBody());
//         logger.info("testCreateRoomFacility executed successfully.");
//     }

//     @Test
//     void testGetRoomFacilityById() {
//         // Arrange
//         String id = "1";
//         RoomFacilityResponse response = RoomFacilityResponse.builder()
//                 .roomId(id)
//                 .roomFacilityName("WiFi")
//                 .build();

//         CommonResponse<RoomFacilityResponse> expectedResponse = CommonResponse.<RoomFacilityResponse>builder()
//                 .statusCode(HttpStatus.OK.value())
//                 .message(HttpStatus.OK.getReasonPhrase())
//                 .data(response)
//                 .build();

//         doReturn(response).when(roomFacilityService).getByIdResponse(id);

//         // Act
//         ResponseEntity<CommonResponse<RoomFacilityResponse>> responseEntity = roomFacilityController.getRoomFacilityById(id);

//         // Assert
//         assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//         assertEquals(expectedResponse, responseEntity.getBody());
//         logger.info("testGetRoomFacilityById executed successfully.");
//     }

//     @Test
//     void testUpdateRoomFacility() {
//         // Arrange
//         RoomFacilityRequest request = RoomFacilityRequest.builder()
//                 .roomFacilityId("1")
//                 .roomFacilityName("Updated WiFi")
//                 .build();

//         RoomFacilityResponse response = RoomFacilityResponse.builder()
//                 .roomId("1")
//                 .roomFacilityName("Updated WiFi")
//                 .build();

//         CommonResponse<RoomFacilityResponse> expectedResponse = CommonResponse.<RoomFacilityResponse>builder()
//                 .statusCode(HttpStatus.OK.value())
//                 .message(HttpStatus.OK.getReasonPhrase())
//                 .data(response)
//                 .build();

//         doReturn(response).when(roomFacilityService).updateResponse(any(RoomFacilityRequest.class));

//         // Act
//         ResponseEntity<CommonResponse<RoomFacilityResponse>> responseEntity = roomFacilityController.updateRoomFacility(request);

//         // Assert
//         assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//         assertEquals(expectedResponse, responseEntity.getBody());
//         logger.info("testUpdateRoomFacility executed successfully.");
//     }

//     @Test
//     void testDeleteRoomFacility() {
//         // Arrange
//         String id = "1";
//         CommonResponse<String> expectedResponse = CommonResponse.<String>builder()
//                 .statusCode(HttpStatus.OK.value())
//                 .message("Removed room facility with id: " + id)
//                 .build();

//         // Act
//         ResponseEntity<CommonResponse<String>> responseEntity = roomFacilityController.deleteRoomFacility(id);

//         // Assert
//         assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//         assertEquals(expectedResponse, responseEntity.getBody());
//         verify(roomFacilityService).delete(id);
//         logger.info("testDeleteRoomFacility executed successfully.");
//     }
// }
