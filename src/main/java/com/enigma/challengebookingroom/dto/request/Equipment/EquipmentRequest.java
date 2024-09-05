package com.enigma.challengebookingroom.dto.request.Equipment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EquipmentRequest {
    private String equipmentName;
}
