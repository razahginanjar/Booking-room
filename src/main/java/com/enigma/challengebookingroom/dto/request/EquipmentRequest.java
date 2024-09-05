package com.enigma.challengebookingroom.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EquipmentRequest {
    private String equipmentName;
}
