package com.enigma.challengebookingroom.controller;

import com.enigma.challengebookingroom.constant.APIUrl;
import com.enigma.challengebookingroom.constant.ConstantReservationStatus;
import com.enigma.challengebookingroom.dto.request.ReservationRequest;
import com.enigma.challengebookingroom.dto.request.UpdateReservationByUser;
import com.enigma.challengebookingroom.dto.request.UpdateReservationRequestByAdmin;
import com.enigma.challengebookingroom.dto.response.CommonResponse;
import com.enigma.challengebookingroom.dto.response.ReservationResponse;
import com.enigma.challengebookingroom.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.RESERVATION)
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<ReservationResponse>> createReservation(
            @RequestBody ReservationRequest request
    ) {
        ReservationResponse create = reservationService.create(request);
        CommonResponse<ReservationResponse> response = CommonResponse.<ReservationResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .data(create)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<ReservationResponse>>> getAllReservations(
            @RequestParam(name = "status") ConstantReservationStatus status
    ) {
        List<ReservationResponse> list = reservationService.getAllByStatus(status);
        CommonResponse<List<ReservationResponse>> response = CommonResponse.<List<ReservationResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(list)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(
            path = APIUrl.PATH_VAR_ID,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<ReservationResponse>> getReservationById(@PathVariable String id) {
        ReservationResponse reservationById = reservationService.getById(id);
        CommonResponse<ReservationResponse> response = CommonResponse.<ReservationResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(reservationById)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // update by user
    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<ReservationResponse>> updateReservationByUser(
            @RequestBody UpdateReservationByUser request
    ) {
        ReservationResponse update = reservationService.update(request);
        CommonResponse<ReservationResponse> response = CommonResponse.<ReservationResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(update)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // update status by admin
    @PutMapping(
            path ="/admin",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<ReservationResponse>> updateReservationByAdmin(
            @RequestBody UpdateReservationRequestByAdmin request
    ) {
        ReservationResponse updatedByAdmin = reservationService.updateByAdmin(request);
        CommonResponse<ReservationResponse> response = CommonResponse.<ReservationResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(updatedByAdmin)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
