package com.enigma.challengebookingroom.mapper.impl;

import com.enigma.challengebookingroom.dto.response.EquipmentResponse;
import com.enigma.challengebookingroom.entity.Equipment;
import com.enigma.challengebookingroom.mapper.EquipmentMapper;
import org.springframework.stereotype.Component;

@Component
public class EquipmentMapperImpl implements EquipmentMapper {
    @Override
    public EquipmentResponse toResponse(Equipment equipment) {
        return EquipmentResponse.builder()
                .equipmentName(equipment.getEquipmentName())
                .build();
    }
}
