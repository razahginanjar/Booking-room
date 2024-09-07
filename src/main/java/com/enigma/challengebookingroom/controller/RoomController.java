package com.enigma.challengebookingroom.controller;

import com.enigma.challengebookingroom.constant.APIUrl;
import com.enigma.challengebookingroom.constant.ConstantMessage;
import com.enigma.challengebookingroom.dto.request.RoomRequest;
import com.enigma.challengebookingroom.dto.response.CommonResponse;
import com.enigma.challengebookingroom.dto.response.RoomResponse;
import com.enigma.challengebookingroom.entity.Room;
import com.enigma.challengebookingroom.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.ROOM)
public class RoomController {
    private final RoomService roomService;

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<RoomResponse>> createRoom(@Valid @RequestBody RoomRequest request) {
        RoomResponse create = roomService.createAndGetResponse(request);
        CommonResponse<RoomResponse> response = CommonResponse.<RoomResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase()) // pesannya gini dulu, ntar ganti aja (sebenernya sama aja sih sama yg di constant message)
                .data(create)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<Room>>> getAllRoom() {
        List<Room> roomList = roomService.getAll();
        CommonResponse<List<Room>> response = CommonResponse.<List<Room>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ConstantMessage.OK) // pesannya gini dulu, ntar ganti aja (sebenernya sama aja sih sama yg di constant message)
                .data(roomList)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(
            path =APIUrl.PATH_VAR_ID,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<RoomResponse>> getRoomById(@PathVariable String id) {
        RoomResponse roomById = roomService.getByIdResponse(id);
        CommonResponse<RoomResponse> response = CommonResponse.<RoomResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase()) // pesannya gini dulu, ntar ganti aja (sebenernya sama aja sih sama yg di constant message)
                .data(roomById)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<RoomResponse>> updateRoom(@Valid @RequestBody RoomRequest request) {
        RoomResponse roomById = roomService.updateAndGetResponse(request);
        CommonResponse<RoomResponse> response = CommonResponse.<RoomResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase()) // pesannya gini dulu, ntar ganti aja (sebenernya sama aja sih sama yg di constant message)
                .data(roomById)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(
            path =APIUrl.PATH_VAR_ID,
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