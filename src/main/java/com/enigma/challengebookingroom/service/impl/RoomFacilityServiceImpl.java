package com.enigma.challengebookingroom.service.impl;

import com.enigma.challengebookingroom.mapper.RoomFacilityMapper;
import com.enigma.challengebookingroom.service.RoomFacilityService;
import com.enigma.challengebookingroom.dto.request.RoomFacilityRequest;
import com.enigma.challengebookingroom.dto.RoomFacilityResponse;
import com.enigma.challengebookingroom.entity.RoomFacility;
import com.enigma.challengebookingroom.repository.RoomFacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RoomFacilityServiceImpl implements RoomFacilityService {

    private final RoomFacilityRepository roomFacilityRepository;

    private final RoomFacilityMapper roomFacilityMapper;

    @Override
    public RoomFacility createAndGet(RoomFacilityRequest updateRoomFacility) {
        RoomFacility build = RoomFacility.builder()
                .roomFacilityName(updateRoomFacility.getRoomFacilityName())
                .build();
        return roomFacilityRepository.saveAndFlush(build);
    }

    @Override
    public RoomFacility getById(String id) {
        return roomFacilityRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Facility not found")
        );
    }

    @Override
    public RoomFacility update(RoomFacilityRequest updateRoomFacility) {
        RoomFacility byId = getById(updateRoomFacility.getRoomFacilityId());
        byId.setRoomFacilityName(updateRoomFacility.getRoomFacilityName());
        return roomFacilityRepository.saveAndFlush(byId);
    }

    @Override
    public void delete(String id) {
        RoomFacility byId = getById(id);
        roomFacilityRepository.delete(byId);
    }

    @Override
    public RoomFacilityResponse createAndGetResponse(RoomFacilityRequest updateRoomFacility) {
        RoomFacility andGet = createAndGet(updateRoomFacility);
        return roomFacilityMapper.toResponse(andGet);
    }

    @Override
    public RoomFacilityResponse getByIdResponse(String id) {
        RoomFacility byId = getById(id);
        return roomFacilityMapper.toResponse(byId);
    }

    @Override
    public RoomFacilityResponse updateResponse(RoomFacilityRequest updateRoomFacility) {
        RoomFacility update = update(updateRoomFacility);
        return roomFacilityMapper.toResponse(update);
    }
}
