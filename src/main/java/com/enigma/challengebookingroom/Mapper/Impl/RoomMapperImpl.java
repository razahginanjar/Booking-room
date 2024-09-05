package com.enigma.challengebookingroom.Mapper.Impl;

import com.enigma.challengebookingroom.Mapper.RoomFacilityMapper;
import com.enigma.challengebookingroom.Mapper.RoomMapper;
import com.enigma.challengebookingroom.dto.response.Room.RoomResponse;
import com.enigma.challengebookingroom.entity.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomMapperImpl implements RoomMapper {
    private final RoomFacilityMapper roomFacilityMapper;

    @Override
    public RoomResponse toResponse(Room room) {
        return RoomResponse.builder()
                .roomCapacity(room.getRoomCapacity())
                .roomFacilities(room.getRoomFacilities().stream().map(
                        roomFacilityMapper::toResponse
                ).toList())
                .roomType(room.getRoomType())
                .vacancy(room.getIsAvailable())
                .build();
    }
}
