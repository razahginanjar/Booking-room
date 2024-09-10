package com.enigma.challengebookingroom.controller;

import com.enigma.challengebookingroom.constant.APIUrl;
import com.enigma.challengebookingroom.constant.ConstantReservationStatus;
import com.enigma.challengebookingroom.dto.request.InsertDateRequest;
import com.enigma.challengebookingroom.dto.request.ReservationRequest;
import com.enigma.challengebookingroom.dto.request.UpdateReservationByAdmin;
import com.enigma.challengebookingroom.dto.request.UpdateReservationStatusByAdmin;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.RESERVATION)
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

    // update status
//    @PutMapping(
//            path =APIUrl.PATH_ADMIN,
//            produces = MediaType.APPLICATION_JSON_VALUE,
//            consumes = MediaType.APPLICATION_JSON_VALUE
//    )
//    public ResponseEntity<CommonResponse<ReservationResponse>> updateReservationByAdmin(
//            @RequestBody UpdateReservationStatusByAdmin request
//    ) {
//        ReservationResponse updatedByAdmin = reservationService.updateByAdmin(request);
//        CommonResponse<ReservationResponse> response = CommonResponse.<ReservationResponse>builder()
//                .statusCode(HttpStatus.OK.value())
//                .message(HttpStatus.OK.getReasonPhrase())
//                .data(updatedByAdmin)
//                .build();
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }

    // patch mapping buat endpoint mail sender
    @PatchMapping(
            path = APIUrl.PATH_STATUS + APIUrl.PATH_VAR_ID,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void updateStatusBySendMail(
            @PathVariable String id,
            @RequestParam(name = "status") ConstantReservationStatus status,
            HttpServletResponse response
    ) throws IOException {
        if (clickedLinks.containsKey(id)) {
            // Redirect to a page informing the user that the link has already been used
            response.sendRedirect("http://localhost:8081/already-clicked");
        } else {
            // Mark the link as clicked
            clickedLinks.put(id, true);
            reservationService.updateStatus(id, status);
            CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .build();
            // Redirect to a success page
            response.sendRedirect("http://localhost:8081/success");
        }
//        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // controller buat get avail room atau equipment disini aja
    @GetMapping(
            path = APIUrl.PATH_AVAIL,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<GetReservationStatusResponse>> getReservationStatus(
            InsertDateRequest request
    ) {
        List<GetReservationStatusResponse> status = reservationService.getStatusReservations(request);
        CommonResponse<GetReservationStatusResponse> response = CommonResponse.<GetReservationStatusResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data((GetReservationStatusResponse) status)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // download csv
    @GetMapping(
            path = APIUrl.PATH_DOWNLOAD,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> downloadReservation(HttpServletResponse response) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        // manggil service kiw kiw disini
        csvService.Download(response);
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Downloaded")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping("/already-clicked")
    public String alreadyClicked() {
        return "<html><body><h1>Already Clicked</h1><p>This link has already been used. Please contact support if you need assistance.</p></body></html>";
    }

    @GetMapping("/success")
    public String success() {
        return "<html><body><h1>Success</h1><p>Your response has been recorded. Thank you!</p></body></html>";
    }
}
