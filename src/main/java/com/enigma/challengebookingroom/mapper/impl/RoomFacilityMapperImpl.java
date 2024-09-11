package com.enigma.challengebookingroom.mapper.impl;

import com.enigma.challengebookingroom.dto.response.RoomFacilityResponse;
import com.enigma.challengebookingroom.entity.RoomFacility;
import com.enigma.challengebookingroom.mapper.RoomFacilityMapper;
import org.springframework.stereotype.Component;

@Component
public class RoomFacilityMapperImpl implements RoomFacilityMapper {
    @Override
    public RoomFacilityResponse toResponse(RoomFacility roomFacility) {
        return RoomFacilityResponse.builder()
                .roomId(roomFacility.getRoomFacilityId())
                .roomFacilityName(roomFacility.getRoomFacilityName())
                .build();
    }
}
