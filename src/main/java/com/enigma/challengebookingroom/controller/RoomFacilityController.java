package com.enigma.challengebookingroom.controller;

import com.enigma.challengebookingroom.constant.APIUrl;
import com.enigma.challengebookingroom.dto.request.RoomFacilityRequest;
import com.enigma.challengebookingroom.dto.response.CommonResponse;
import com.enigma.challengebookingroom.dto.response.RoomFacilityResponse;
import com.enigma.challengebookingroom.service.RoomFacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// aku bikin ini buat jaga2

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.ROOM_FACILITY)
public class RoomFacilityController {
    private final RoomFacilityService roomFacilityService;

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<RoomFacilityResponse>> createRoomFacility(
            @RequestBody RoomFacilityRequest roomFacility
    ) {
        RoomFacilityResponse create = roomFacilityService.createAndGetResponse(roomFacility);
        CommonResponse<RoomFacilityResponse> response = CommonResponse.<RoomFacilityResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .data(create)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping(
            path = APIUrl.PATH_VAR_ID,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<RoomFacilityResponse>> getRoomFacilityById(
            @PathVariable String id
    ) {
        RoomFacilityResponse byId = roomFacilityService.getByIdResponse(id);
        CommonResponse<RoomFacilityResponse> response = CommonResponse.<RoomFacilityResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(byId)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<RoomFacilityResponse>> updateRoomFacility(
            @RequestBody RoomFacilityRequest roomFacility
    ) {
        RoomFacilityResponse updated = roomFacilityService.updateResponse(roomFacility);
        CommonResponse<RoomFacilityResponse> response = CommonResponse.<RoomFacilityResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(updated)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping(
            path = APIUrl.PATH_VAR_ID,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> deleteRoomFacility(@PathVariable String id) {
        roomFacilityService.delete(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Removed room facility with id: " + id)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
