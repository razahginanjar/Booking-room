package com.enigma.challengebookingroom.dto.request;

import com.enigma.challengebookingroom.constant.ConstantEquipmentStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EquipmentStatusRequest implements Serializable {
    String equipmentStatusId;
    ConstantEquipmentStatus equipmentStatusName;
}