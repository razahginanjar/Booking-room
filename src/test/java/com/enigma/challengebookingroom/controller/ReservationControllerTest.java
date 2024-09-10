package com.enigma.challengebookingroom.controller;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.enigma.challengebookingroom.constant.ConstantReservationStatus;
import com.enigma.challengebookingroom.dto.request.ReservationRequest;
import com.enigma.challengebookingroom.dto.request.UpdateReservationByAdmin;
import com.enigma.challengebookingroom.dto.response.CommonResponse;
import com.enigma.challengebookingroom.dto.response.ReservationResponse;
import com.enigma.challengebookingroom.service.ReservationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    public ReservationControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateReservation() {
        // Arrange
        ReservationRequest request = new ReservationRequest();
        ReservationResponse response = new ReservationResponse();
        when(reservationService.create(any(ReservationRequest.class))).thenReturn(response);

        // Act
        ResponseEntity<CommonResponse<ReservationResponse>> responseEntity = reservationController.createReservation(request);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(HttpStatus.CREATED.getReasonPhrase(), responseEntity.getBody().getMessage());
        assertEquals(response, responseEntity.getBody().getData());
        verify(reservationService, times(1)).create(request);
        log.info("Test passed: testCreateReservation");
    }

    @Test
    void testGetAllReservations() {
        // Arrange
        ConstantReservationStatus status = ConstantReservationStatus.PENDING;
        List<ReservationResponse> responses = Collections.singletonList(new ReservationResponse());
        when(reservationService.getAllByStatus(status)).thenReturn(responses);

        // Act
        ResponseEntity<CommonResponse<List<ReservationResponse>>> responseEntity = reservationController.getAllReservations(status);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseEntity.getBody().getMessage());
        assertEquals(responses, responseEntity.getBody().getData());
        verify(reservationService, times(1)).getAllByStatus(status);
        log.info("Test passed: testGetAllReservations");
    }

    @Test
    void testGetReservationById() {
        // Arrange
        String id = "1";
        ReservationResponse response = new ReservationResponse();
        when(reservationService.getById(id)).thenReturn(response);

        // Act
        ResponseEntity<CommonResponse<ReservationResponse>> responseEntity = reservationController.getReservationById(id);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseEntity.getBody().getMessage());
        assertEquals(response, responseEntity.getBody().getData());
        verify(reservationService, times(1)).getById(id);
        log.info("Test passed: testGetReservationById");
    }

    @Test
    void testUpdateReservationByUser() {
        // Arrange
        UpdateReservationByAdmin request = new UpdateReservationByAdmin();
        ReservationResponse response = new ReservationResponse();
        when(reservationService.update(any(UpdateReservationByAdmin.class))).thenReturn(response);

        // Act
        ResponseEntity<CommonResponse<ReservationResponse>> responseEntity = reservationController.updateReservationByUser(request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseEntity.getBody().getMessage());
        assertEquals(response, responseEntity.getBody().getData());
        verify(reservationService, times(1)).update(request);
        log.info("Test passed: testUpdateReservationByUser");
    }
}

