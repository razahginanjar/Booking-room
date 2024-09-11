package com.enigma.challengebookingroom.controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.enigma.challengebookingroom.constant.ConstantReservationStatus;
import com.enigma.challengebookingroom.dto.request.ReservationRequest;
import com.enigma.challengebookingroom.dto.request.Booking.Deprecated_UpdateReservationByAdmin;
import com.enigma.challengebookingroom.dto.response.CommonResponse;
import com.enigma.challengebookingroom.dto.response.ReservationResponse;
import com.enigma.challengebookingroom.service.ReservationService;
import com.enigma.challengebookingroom.service.impl.CsvService;
import com.mailjet.client.errors.MailjetException;

class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ReservationService reservationService;

    @Mock
    private CsvService csvService;

    @InjectMocks
    private ReservationController reservationController;

    public ReservationControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
    }

    @SuppressWarnings("null")
    @Test
    void createReservation() throws MailjetException {
        // Arrange
        ReservationRequest request = new ReservationRequest();
        ReservationResponse response = new ReservationResponse();
        when(reservationService.create(any(ReservationRequest.class))).thenReturn(response);

        // Act
        ResponseEntity<CommonResponse<ReservationResponse>> result = reservationController.createReservation(request);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody().getData());
    }

    @SuppressWarnings("null")
    @Test
    void getAllReservations() {
        // Arrange
        ConstantReservationStatus status = ConstantReservationStatus.PENDING;
        List<ReservationResponse> responseList = List.of(new ReservationResponse());
        when(reservationService.getAllByStatus(status)).thenReturn(responseList);

        // Act
        ResponseEntity<CommonResponse<List<ReservationResponse>>> result = reservationController.getAllReservations(status);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody().getData());
    }

    @SuppressWarnings("null")
    @Test
    void getReservationById() {
        // Arrange
        String id = "123";
        ReservationResponse response = new ReservationResponse();
        when(reservationService.getById(id)).thenReturn(response);

        // Act
        ResponseEntity<CommonResponse<ReservationResponse>> result = reservationController.getReservationById(id);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody().getData());
    }

    @SuppressWarnings("null")
    @Test
    void updateReservationByUser() {
        // Arrange
        Deprecated_UpdateReservationByAdmin request = new Deprecated_UpdateReservationByAdmin();
        ReservationResponse response = new ReservationResponse();
        when(reservationService.update(any(Deprecated_UpdateReservationByAdmin.class))).thenReturn(response);

        // Act
        ResponseEntity<CommonResponse<ReservationResponse>> result = reservationController.updateReservationByUser(request);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody().getData());
    }

    // @Test
    // void testGetReservationStatus() throws Exception {
    // // Arrange
    // InsertDateRequest request = new InsertDateRequest();
    // request.setDate("2024-09-10");

    // GetReservationStatusResponse statusResponse = new GetReservationStatusResponse();

    // List<GetReservationStatusResponse> statusList = List.of(statusResponse);

    // when(reservationService.getStatusReservations(any(InsertDateRequest.class)))
    //         .thenReturn(statusList);

    // mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();

    // // Act
    // MvcResult mvcResult = mockMvc.perform(get(APIUrl.RESERVATION + APIUrl.PATH_AVAIL)
    //         .param("date", "2024-09-10")
    //         .accept(MediaType.APPLICATION_JSON))
    //         .andExpect(status().isOk())
    //         .andReturn();

    // // Assert
    // String content = mvcResult.getResponse().getContentAsString();
    // assertThat(content).contains("\"statusCode\":200")
    //     .contains("\"message\":\"OK\"")
    //     .contains("\"data\":[{\"");
    // }



    // @SuppressWarnings("null")
    // @Test
    // void downloadReservation() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
    //     // Arrange
    //     doNothing().when(csvService).Download(any(HttpServletResponse.class));

    //     // Act
    //     ResponseEntity<CommonResponse<String>> result = reservationController.downloadReservation(mock(HttpServletResponse.class));

    //     // Assert
    //     assertEquals(HttpStatus.OK, result.getStatusCode());
    //     assertEquals("Downloaded", result.getBody().getMessage());
    // }
}
