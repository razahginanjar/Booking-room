package com.enigma.challengebookingroom.Mapper.Impl;

import com.enigma.challengebookingroom.Mapper.RoomFacilityMapper;
import com.enigma.challengebookingroom.dto.response.RoomFacility.RoomFacilityResponse;
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
