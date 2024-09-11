package com.enigma.challengebookingroom.controller;

import com.enigma.challengebookingroom.constant.APIUrl;
import com.enigma.challengebookingroom.constant.ConstantMessage;
import com.enigma.challengebookingroom.dto.request.RoomRequest;
import com.enigma.challengebookingroom.dto.response.CommonResponse;
import com.enigma.challengebookingroom.dto.response.RoomResponse;
import com.enigma.challengebookingroom.entity.Room;
import com.enigma.challengebookingroom.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.ROOM)
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Room")
public class RoomController {
    private final RoomService roomService;

    @Operation(
            description = "Add room to DB(ADMIN PRIVILEGE)",
            summary = "Add room "
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<RoomResponse>> createRoom(@Valid @RequestBody RoomRequest request) {
        RoomResponse create = roomService.createAndGetResponse(request);
        CommonResponse<RoomResponse> response = CommonResponse.<RoomResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .data(create)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            description = "Get all room",
            summary = "Get all room "
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<Room>>> getAllRoom() {
        List<Room> roomList = roomService.getAll();
        CommonResponse<List<Room>> response = CommonResponse.<List<Room>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ConstantMessage.OK)
                .data(roomList)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            description = "Get specific room",
            summary = "Get specific room "
    )
    @GetMapping(
            path = APIUrl.PATH_VAR_ID,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<RoomResponse>> getRoomById(@PathVariable String id) {
        RoomResponse roomById = roomService.getByIdResponse(id);
        CommonResponse<RoomResponse> response = CommonResponse.<RoomResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(roomById)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            description = "Update Room (ADMIN PRIVILEGE)",
            summary = "Update Room"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<RoomResponse>> updateRoom(@Valid @RequestBody RoomRequest request) {
        RoomResponse roomById = roomService.updateAndGetResponse(request);
        CommonResponse<RoomResponse> response = CommonResponse.<RoomResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(roomById)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            description = "Delete specific room (ADMIN PRIVILEGE)",
            summary = "Delete specific room"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping(
            path = APIUrl.PATH_VAR_ID,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> deleteRoomById(@PathVariable String id) {
        roomService.deleteRoom(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Deleted room with id: " + id)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}