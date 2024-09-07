package com.enigma.challengebookingroom.service;

import com.enigma.challengebookingroom.dto.request.EquipmentRequest;
import com.enigma.challengebookingroom.dto.response.EquipmentResponse;
import com.enigma.challengebookingroom.entity.Equipment;

public interface EquipmentService {
    Equipment create(EquipmentRequest equipmentRequest);
    Equipment getById(String id);
    Equipment update(EquipmentRequest equipmentRequest);
    void deleteById(String id);

    EquipmentResponse createResponse(EquipmentRequest equipmentRequest);
    EquipmentResponse getResponseById(String id);
    EquipmentResponse updateResponse(EquipmentRequest equipmentRequest);
}
