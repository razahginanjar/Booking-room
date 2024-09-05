package com.enigma.challengebookingroom.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomFacilityRequest implements Serializable {
    String roomFacilityId;
    String roomFacilityName;
}