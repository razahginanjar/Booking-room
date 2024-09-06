package com.enigma.challengebookingroom.mapper;

import com.enigma.challengebookingroom.dto.response.EquipmentResponse;
import com.enigma.challengebookingroom.entity.Equipment;

public interface EquipmentMapper {
    EquipmentResponse toResponse(Equipment equipment);
}
