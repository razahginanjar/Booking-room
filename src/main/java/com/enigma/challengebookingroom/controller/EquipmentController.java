package com.enigma.challengebookingroom.controller;

import com.enigma.challengebookingroom.constant.APIUrl;
import com.enigma.challengebookingroom.dto.request.EquipmentRequest;
import com.enigma.challengebookingroom.dto.response.CommonResponse;
import com.enigma.challengebookingroom.dto.response.EquipmentResponse;
import com.enigma.challengebookingroom.entity.Equipment;
import com.enigma.challengebookingroom.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.EQUIPMENT)
public class EquipmentController {
    private final EquipmentService equipmentService;

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<EquipmentResponse>> createEquipment(
            @RequestBody EquipmentRequest equipment
    ) {
        EquipmentResponse create = equipmentService.createResponse(equipment);
        CommonResponse<EquipmentResponse> response = CommonResponse.<EquipmentResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .data(create)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<EquipmentResponse>>> getAllEquipment() {
        List<EquipmentResponse> list = equipmentService.getAll();
        CommonResponse<List<EquipmentResponse>> response = CommonResponse.<List<EquipmentResponse>>builder()
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
    public ResponseEntity<CommonResponse<EquipmentResponse>> getEquipmentById(@PathVariable String id) {
        EquipmentResponse byId = equipmentService.getResponseById(id);
        CommonResponse<EquipmentResponse> response = CommonResponse.<EquipmentResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(byId)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<EquipmentResponse>> updateEquipment(
            @RequestBody EquipmentRequest equipment
    ) {
        EquipmentResponse update = equipmentService.updateResponse(equipment);
        CommonResponse<EquipmentResponse> response = CommonResponse.<EquipmentResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(update)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(
            path = APIUrl.PATH_VAR_ID,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> deleteEquipment(@PathVariable String id) {
        equipmentService.getById(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Removed equipment with id: " + id)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
