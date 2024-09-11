package com.enigma.challengebookingroom.controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.enigma.challengebookingroom.constant.ConstantMessage;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enigma.challengebookingroom.constant.APIUrl;
import com.enigma.challengebookingroom.constant.ConstantReservationStatus;
import com.enigma.challengebookingroom.dto.request.InsertDateRequest;
import com.enigma.challengebookingroom.dto.request.ReservationRequest;
import com.enigma.challengebookingroom.dto.request.UpdateReservationByAdmin;
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
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.RESERVATION)
@WebMvcTest(ReservationController.class)
public class ReservationController {
    private final ReservationService reservationService;
    private final CsvService csvService;
    private final Map<String, Boolean> clickedLinks = new HashMap<>();

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

    // update reservation data
    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<ReservationResponse>> updateReservationByUser(
            @RequestBody UpdateReservationByAdmin request
    ) {
        ReservationResponse update = reservationService.update(request);
        CommonResponse<ReservationResponse> response = CommonResponse.<ReservationResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(update)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping(
            path = APIUrl.PATH_STATUS + APIUrl.PATH_VAR_ID
    )
    public void updateStatusBySendMail(
            @PathVariable String id,
            @RequestParam(name = "action") ConstantReservationStatus status,
            HttpServletResponse response
    ) throws IOException {
        if (clickedLinks.containsKey(id)) {
            // Redirect to a page informing the user that the link has already been used
            response.sendRedirect("http://localhost:8081"+APIUrl.RESERVATION+APIUrl.ALREADY_CLICK);
        } else {
            // Mark the link as clicked
            clickedLinks.put(id, true);
            reservationService.updateStatus(id, status);
            // Redirect to a success page
            response.sendRedirect("http://localhost:8081"+APIUrl.RESERVATION+APIUrl.SUCCESS);
        }
    }

    // controller buat get avail room atau equipment disini aja
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

    // download csv
    @PreAuthorize("hasRole('GENERAL_AFFAIR')")
    @GetMapping(
            path = APIUrl.PATH_DOWNLOAD,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void downloadReservation(HttpServletResponse response) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        csvService.Download(response);
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Downloaded")
                .build();
    }

    @GetMapping(APIUrl.ALREADY_CLICK)
    public String alreadyClicked() {
        return ConstantMessage.ALREADY_CLICK_HTML;
    }

    @GetMapping(APIUrl.SUCCESS)
    public String success() {
        return ConstantMessage.SUCCESS_CLICKED;
    }

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
