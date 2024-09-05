package com.enigma.challengebookingroom.controller;

import com.enigma.challengebookingroom.constant.APIUrl;
import com.enigma.challengebookingroom.dto.response.CommonResponse;
import com.enigma.challengebookingroom.entity.User;
import com.enigma.challengebookingroom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// ini aku buat cuma buat ngetest controller servicenya aja
// nanti mungkin kalo udah jadi semua lebih baik semua mending di handle sama employee aja
// soalnya user service cuma ngehandle masalah perakunan auth sama authorize

@RestController
@RequiredArgsConstructor
@RequestMapping(APIUrl.USER)
public class UserController {
    private final UserService userService;

    @GetMapping(
            path = APIUrl.PATH_VAR_ID,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<User>> getRoomById(@PathVariable String id) {
        User user = userService.getById(id);
        CommonResponse<User> response = CommonResponse.<User>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase()) // pesannya gini dulu, ntar ganti aja (sebenernya sama aja sih sama yg di constant message)
                .data(user)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(
            path = APIUrl.PATH_VAR_ID,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> deleteRoomById(@PathVariable String id) {
        userService.remove(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Removed account with id: " + id)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}