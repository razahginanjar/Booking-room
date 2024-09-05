package com.enigma.challengebookingroom.dto.response;

import com.enigma.challengebookingroom.dto.RoomFacilityResponse;
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
    private String roomType;

    private Integer roomCapacity;

    private List<RoomFacilityResponse> roomFacilities;

    private Boolean isAvailable;

}
