// package com.enigma.challengebookingroom.controller;

// import java.util.Arrays;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import static org.mockito.ArgumentMatchers.any;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;
// import org.mockito.MockitoAnnotations;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.PathVariable;

// import com.enigma.challengebookingroom.constant.APIUrl;
// import com.enigma.challengebookingroom.dto.request.EquipmentRequest;
// import com.enigma.challengebookingroom.dto.response.CommonResponse;
// import com.enigma.challengebookingroom.dto.response.EquipmentResponse;
// import com.enigma.challengebookingroom.service.EquipmentService;

// class EquipmentControllerTest {

//     private static final Logger logger = LoggerFactory.getLogger(EquipmentControllerTest.class);

//     @Mock
//     private EquipmentService equipmentService;

//     @InjectMocks
//     private EquipmentController equipmentController;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     void testCreateEquipment() {
//         // Arrange
//         EquipmentRequest request = new EquipmentRequest("1", "Projector");
//         EquipmentResponse response = EquipmentResponse.builder()
//                 .equipmentId("1")
//                 .equipmentName("Projector")
//                 .build();
//         when(equipmentService.createResponse(any(EquipmentRequest.class))).thenReturn(response);

//         // Act
//         ResponseEntity<CommonResponse<EquipmentResponse>> responseEntity = equipmentController.createEquipment(request);

//         // Assert
//         assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//         assertEquals(response, responseEntity.getBody().getData());
//         verify(equipmentService, times(1)).createResponse(request);
//         logger.info("Test passed: testCreateEquipment");
//     }

//     @Test
//     void testGetAllEquipment() {
//         // Arrange
//         List<EquipmentResponse> list = Arrays.asList(
//                 new EquipmentResponse("1", "Projector"),
//                 new EquipmentResponse("2", "Speaker")
//         );
//         when(equipmentService.getAll()).thenReturn(list);

//         // Act
//         ResponseEntity<CommonResponse<List<EquipmentResponse>>> responseEntity = equipmentController.getAllEquipment();

//         // Assert
//         assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//         assertEquals(list, responseEntity.getBody().getData());
//         verify(equipmentService, times(1)).getAll();
//         logger.info("Test passed: testGetAllEquipment");
//     }

//     @Test
//     void testGetEquipmentById() {
//         // Arrange
//         String equipmentId = "1";
//         EquipmentResponse response = EquipmentResponse.builder()
//                 .equipmentId(equipmentId)
//                 .equipmentName("Projector")
//                 .build();
//         when(equipmentService.getResponseById(equipmentId)).thenReturn(response);

//         // Act
//         ResponseEntity<CommonResponse<EquipmentResponse>> responseEntity = equipmentController.getEquipmentById(equipmentId);

//         // Assert
//         assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//         assertEquals(response, responseEntity.getBody().getData());
//         verify(equipmentService, times(1)).getResponseById(equipmentId);
//         logger.info("Test passed: testGetEquipmentById");
//     }

//     @Test
//     void testUpdateEquipment() {
//         // Arrange
//         EquipmentRequest request = new EquipmentRequest("1", "Updated Projector");
//         EquipmentResponse updatedResponse = EquipmentResponse.builder()
//                 .equipmentId("1")
//                 .equipmentName("Updated Projector")
//                 .build();
//         when(equipmentService.updateResponse(any(EquipmentRequest.class))).thenReturn(updatedResponse);

//         // Act
//         ResponseEntity<CommonResponse<EquipmentResponse>> responseEntity = equipmentController.updateEquipment(request);

//         // Assert
//         assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//         assertEquals(updatedResponse, responseEntity.getBody().getData());
//         verify(equipmentService, times(1)).updateResponse(request);
//         logger.info("Test passed: testUpdateEquipment");
//     }

//     @DeleteMapping(
//             path = APIUrl.PATH_VAR_ID,
//             produces = MediaType.APPLICATION_JSON_VALUE
//     )
//     public ResponseEntity<CommonResponse<String>> deleteEquipment(@PathVariable String id) {
//         equipmentService.deleteById(id); // This should be the call to deleteById
//         CommonResponse<String> response = CommonResponse.<String>builder()
//                 .statusCode(HttpStatus.OK.value())
//                 .message("Removed equipment with id: " + id)
//                 .build();
//         return ResponseEntity.status(HttpStatus.OK).body(response);
//     }

// }


