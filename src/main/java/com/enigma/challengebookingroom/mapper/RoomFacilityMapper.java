package com.enigma.challengebookingroom.mapper;

import com.enigma.challengebookingroom.dto.RoomFacilityResponse;
import com.enigma.challengebookingroom.entity.RoomFacility;

public interface RoomFacilityMapper {
    RoomFacilityResponse toResponse(RoomFacility roomFacility);
}
