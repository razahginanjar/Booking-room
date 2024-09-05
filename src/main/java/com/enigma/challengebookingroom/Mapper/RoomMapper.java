package com.enigma.challengebookingroom.Mapper;

import com.enigma.challengebookingroom.dto.response.Room.RoomResponse;
import com.enigma.challengebookingroom.entity.Room;

public interface RoomMapper {
    RoomResponse toResponse(Room room);
}
