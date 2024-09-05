package com.enigma.challengebookingroom.Service.Impl;

import com.enigma.challengebookingroom.Mapper.RoomMapper;
import com.enigma.challengebookingroom.Service.RoomService;
import com.enigma.challengebookingroom.dto.request.RoomRequest;
import com.enigma.challengebookingroom.dto.response.Room.RoomResponse;
import com.enigma.challengebookingroom.entity.Room;
import com.enigma.challengebookingroom.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    public Room getById(String id) {
        return roomRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room is not found")
        );
    }

    @Override
    public Room createAndGet(RoomRequest request) {
        Room room = Room.builder()
                .roomCapacity(request.getRoomCapacity())
                .roomType(request.getRoomType())
                .isAvailable(request.getIsAvailable())
                .build();
        return roomRepository.saveAndFlush(room);
    }

    @Override
    public Room updateAndGet(RoomRequest request) {
        Room byId = getById(request.getRoomId());
        byId.setRoomCapacity(request.getRoomCapacity());
        byId.setIsAvailable(request.getIsAvailable());
        byId.setRoomType(request.getRoomType());
        return roomRepository.saveAndFlush(byId);
    }

    @Override
    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> getAllByAvailable(Boolean isAvailable) {
        return roomRepository.findAllByIsAvailable(isAvailable);
    }

    @Override
    public void deleteRoom(String id) {
        Room byId = getById(id);
        roomRepository.delete(byId);
    }

    @Override
    public RoomResponse getByIdResponse(String id) {
        Room byId = getById(id);
        return roomMapper.toResponse(byId);
    }

    @Override
    public RoomResponse createAndGetResponse(RoomRequest request) {
        Room room = createAndGet(request);
        return roomMapper.toResponse(room);
    }

    @Override
    public RoomResponse updateAndGetResponse(RoomRequest request) {
        Room room = updateAndGet(request);
        return roomMapper.toResponse(room);
    }
}
