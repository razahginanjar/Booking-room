package com.enigma.challengebookingroom.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomFacilityResponse {
    private String roomId;
    private String roomFacilityName;
}
