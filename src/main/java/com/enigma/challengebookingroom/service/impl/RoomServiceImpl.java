package com.enigma.challengebookingroom.service.impl;

import com.enigma.challengebookingroom.mapper.RoomMapper;
import com.enigma.challengebookingroom.service.RoomService;
import com.enigma.challengebookingroom.dto.request.RoomRequest;
import com.enigma.challengebookingroom.dto.response.RoomResponse;
import com.enigma.challengebookingroom.entity.Room;
import com.enigma.challengebookingroom.repository.RoomRepository;
import com.enigma.challengebookingroom.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final ValidationUtils validator;

    @Transactional(readOnly = true)
    @Override
    public Room getById(String id) {
        return roomRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase())
        );
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Room createAndGet(RoomRequest request) {
        validator.validate(request);
        Room room = Room.builder()
                .roomCapacity(request.getRoomCapacity())
                .roomType(request.getRoomType())
                .build();
        return roomRepository.saveAndFlush(room);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Room updateAndGet(RoomRequest request) {
        validator.validate(request);
        Room byId = getById(request.getRoomId());
        byId.setRoomCapacity(request.getRoomCapacity());
        byId.setRoomType(request.getRoomType());
        return roomRepository.saveAndFlush(byId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRoom(String id) {
        Room byId = getById(id);
        roomRepository.delete(byId);
    }

    @Transactional(readOnly = true)
    @Override
    public RoomResponse getByIdResponse(String id) {
        Room byId = getById(id);
        return roomMapper.toResponse(byId);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public RoomResponse createAndGetResponse(RoomRequest request) {
        Room room = createAndGet(request);
        return roomMapper.toResponse(room);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public RoomResponse updateAndGetResponse(RoomRequest request) {
        Room room = updateAndGet(request);
        return roomMapper.toResponse(room);
    }
}
