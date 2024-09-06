package com.enigma.challengebookingroom.mapper.impl;

import com.enigma.challengebookingroom.mapper.RoomFacilityMapper;
import com.enigma.challengebookingroom.dto.response.RoomFacilityResponse;
import com.enigma.challengebookingroom.entity.RoomFacility;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class RoomFacilityMapperImpl implements RoomFacilityMapper {
    @Override
    public RoomFacilityResponse toResponse(RoomFacility roomFacility) {
        return RoomFacilityResponse.builder()
                .roomFacilityName(roomFacility.getRoomFacilityName())
                .build();
    }
}
