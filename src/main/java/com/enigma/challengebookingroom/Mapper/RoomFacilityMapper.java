package com.enigma.challengebookingroom.Mapper;

import com.enigma.challengebookingroom.dto.response.RoomFacility.RoomFacilityResponse;
import com.enigma.challengebookingroom.entity.RoomFacility;

public interface RoomFacilityMapper {
    RoomFacilityResponse toResponse(RoomFacility roomFacility);
}
