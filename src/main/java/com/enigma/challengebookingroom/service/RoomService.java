package com.enigma.challengebookingroom.service;

import com.enigma.challengebookingroom.dto.request.RoomRequest;
import com.enigma.challengebookingroom.dto.response.RoomResponse;
import com.enigma.challengebookingroom.entity.Room;

import java.util.List;

public interface RoomService {
    Room getById(String id);
    Room createAndGet(RoomRequest request);
    Room updateAndGet(RoomRequest request);
    List<Room> getAll();
    List<Room> getAllByAvailable(Boolean isAvailable);
    void deleteRoom(String id);

    RoomResponse getByIdResponse(String id);
    RoomResponse createAndGetResponse(RoomRequest request);
    RoomResponse updateAndGetResponse(RoomRequest request);
}
