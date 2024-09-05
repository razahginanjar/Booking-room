package com.enigma.challengebookingroom.mapper.impl;

import com.enigma.challengebookingroom.mapper.RoomFacilityMapper;
import com.enigma.challengebookingroom.dto.RoomFacilityResponse;
import com.enigma.challengebookingroom.entity.RoomFacility;
import org.springframework.stereotype.Service;

@Service
public class RoomFacilityMapperImpl implements RoomFacilityMapper {
    @Override
    public RoomFacilityResponse toResponse(RoomFacility roomFacility) {
        return RoomFacilityResponse.builder()
                .facility(roomFacility.getRoomFacilityName())
                .build();
    }
}
