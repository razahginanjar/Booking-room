package com.enigma.challengebookingroom.mapper.impl;

import com.enigma.challengebookingroom.mapper.RoomFacilityMapper;
import com.enigma.challengebookingroom.mapper.RoomMapper;
import com.enigma.challengebookingroom.dto.response.RoomResponse;
import com.enigma.challengebookingroom.entity.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RoomMapperImpl implements RoomMapper {
    private final RoomFacilityMapper roomFacilityMapper;

    @Override
    public RoomResponse toResponse(Room room) {
        RoomResponse build = RoomResponse.builder()
                .roomId(room.getRoomId())
                .roomCapacity(room.getRoomCapacity())
                .roomType(room.getRoomType())
                .build();
        if(Objects.nonNull(room.getRoomFacilities() ))
        {
            build.setRoomFacilities(room.getRoomFacilities().stream().map(
                    roomFacilityMapper::toResponse
            ).toList());
        }
        return build;
    }
}
