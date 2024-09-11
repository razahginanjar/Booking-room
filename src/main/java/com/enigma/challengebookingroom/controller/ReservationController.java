package com.enigma.challengebookingroom.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.enigma.challengebookingroom.constant.ConstantMessage;
import com.enigma.challengebookingroom.dto.request.UpdateReservationStatusByAdmin;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.enigma.challengebookingroom.constant.APIUrl;
import com.enigma.challengebookingroom.constant.ConstantReservationStatus;
import com.enigma.challengebookingroom.dto.request.InsertDateRequest;
import com.enigma.challengebookingroom.dto.request.ReservationRequest;
import com.enigma.challengebookingroom.dto.response.CommonResponse;
import com.enigma.challengebookingroom.dto.response.GetReservationStatusResponse;
import com.enigma.challengebookingroom.dto.response.ReservationResponse;
import com.enigma.challengebookingroom.service.ReservationService;
import com.enigma.challengebookingroom.service.impl.CsvService;
import com.mailjet.client.errors.MailjetException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = APIUrl.RESERVATION)
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@WebMvcTest(ReservationController.class)
@Tag(name = "Reservation")
public class ReservationController {
    private final ReservationService reservationService;
    @Value("${challengebookingroom.API_URL_SERVER}")
    private  String URL_SERVER;
    private final CsvService csvService;
    private final Map<String, Boolean> clickedLinks = new HashMap<>();

    @Operation(
            description = "Add Reservation to DB(ADMIN PRIVILEGE)",
            summary = "Add Reservation "
    )
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<ReservationResponse>> createReservation(
            @RequestBody ReservationRequest request
    ) throws MailjetException {
        ReservationResponse create = reservationService.create(request);
        CommonResponse<ReservationResponse> response = CommonResponse.<ReservationResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .data(create)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            description = "Get all reservation(ADMIN PRIVILEGE)",
            summary = "Get all reservation"
    )
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'GENERAL_AFFAIR')")
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

    @Operation(
            description = "Get specific reservation (ADMIN, AND GENERAL_AFFAIR PRIVILEGE)",
            summary = "Get specific reservation"
    )
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'GENERAL_AFFAIR')")
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

    @Operation(
            description = "Update reservation information (ADMIN PRIVILEGE)",
            summary = "Update reservation information"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PatchMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<ReservationResponse>> updateReservationCanceled(
            @RequestBody UpdateReservationStatusByAdmin request
    ) {
        ReservationResponse update = reservationService.updateCanceled(request);
        CommonResponse<ReservationResponse> response = CommonResponse.<ReservationResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(update)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Hidden
    @GetMapping(
            path = APIUrl.PATH_STATUS + APIUrl.PATH_VAR_ID
    )
    public void updateStatusBySendMail(
            @PathVariable String id,
            @RequestParam(name = "action") ConstantReservationStatus status,
            HttpServletResponse response
    ) throws IOException {
        if (clickedLinks.containsKey(id)) {
            response.sendRedirect(URL_SERVER+APIUrl.RESERVATION+APIUrl.ALREADY_CLICK);
        } else {
            clickedLinks.put(id, true);
            reservationService.updateStatus(id, status);

            response.sendRedirect(URL_SERVER+APIUrl.RESERVATION+APIUrl.SUCCESS);
        }
    }
    @Operation(
            description = "Get reservation based on date",
            summary = "Get reservation based on date"
    )
    @GetMapping(
            path = APIUrl.PATH_AVAIL,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<List<GetReservationStatusResponse>>> getReservationStatus(
            @RequestBody InsertDateRequest request
    ) {
        List<GetReservationStatusResponse> status = reservationService.getStatusReservations(request);
        CommonResponse<List<GetReservationStatusResponse>> response = CommonResponse.<List<GetReservationStatusResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(status)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @Operation(
            description = "Download data reservation in csv",
            summary = "Download data reservation"
    )
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'GENERAL_AFFAIR')")
    @GetMapping(
            path = APIUrl.PATH_DOWNLOAD,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void downloadReservation(HttpServletResponse response)
            throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        csvService.Download(response);
    }

    @Hidden
    @GetMapping(APIUrl.ALREADY_CLICK)
    public String alreadyClicked() {
        return ConstantMessage.ALREADY_CLICK_HTML;
    }

    @Hidden
    @GetMapping(APIUrl.SUCCESS)
    public String success() {
        return ConstantMessage.SUCCESS_CLICKED;
    }

    @Operation(
            description = "Get data reservation based on employee",
            summary = "Get data reservation"
    )
    @GetMapping(
            path = "/employee",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<ReservationResponse>>> getAllByEmployee() {
        List<ReservationResponse> list = reservationService.historyOfCustomer();
        CommonResponse<List<ReservationResponse>> response = CommonResponse.<List<ReservationResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data((list))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
