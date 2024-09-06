package com.enigma.challengebookingroom.mapper.impl;

import com.enigma.challengebookingroom.mapper.RoomFacilityMapper;
import com.enigma.challengebookingroom.mapper.RoomMapper;
import com.enigma.challengebookingroom.dto.response.RoomResponse;
import com.enigma.challengebookingroom.entity.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class RoomMapperImpl implements RoomMapper {
    private final RoomFacilityMapper roomFacilityMapper;

    @Override
    public RoomResponse toResponse(Room room) {
        return RoomResponse.builder()
                .roomId(room.getRoomId())
                .roomCapacity(room.getRoomCapacity())
                .roomFacilities(room.getRoomFacilities().stream().map(
                        roomFacilityMapper::toResponse
                ).toList())
                .roomType(room.getRoomType())
                .isAvailable(room.getIsAvailable())
                .build();
    }
}
