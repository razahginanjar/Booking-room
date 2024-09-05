package com.enigma.challengebookingroom.mapper;

import com.enigma.challengebookingroom.dto.response.RoomResponse;
import com.enigma.challengebookingroom.entity.Room;

public interface RoomMapper {
    RoomResponse toResponse(Room room);
}
