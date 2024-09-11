package com.enigma.challengebookingroom.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomResponse {
    private String roomId;
    private String roomType;
    private Integer roomCapacity;
    private List<RoomFacilityResponse> roomFacilities;
}
