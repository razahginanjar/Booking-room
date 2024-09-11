package com.enigma.challengebookingroom.service.impl;

import com.enigma.challengebookingroom.dto.request.RoomFacilityRequest;
import com.enigma.challengebookingroom.dto.response.RoomFacilityResponse;
import com.enigma.challengebookingroom.entity.RoomFacility;
import com.enigma.challengebookingroom.mapper.RoomFacilityMapper;
import com.enigma.challengebookingroom.repository.RoomFacilityRepository;
import com.enigma.challengebookingroom.service.RoomFacilityService;
import com.enigma.challengebookingroom.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RoomFacilityServiceImpl implements RoomFacilityService {

    private final RoomFacilityRepository roomFacilityRepository;

    private final RoomFacilityMapper roomFacilityMapper;

    private final ValidationUtils validator;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RoomFacility createAndGet(RoomFacilityRequest updateRoomFacility) {
        validator.validate(updateRoomFacility);
        RoomFacility build = RoomFacility.builder()
                .roomFacilityName(updateRoomFacility.getRoomFacilityName())
                .build();
        return roomFacilityRepository.saveAndFlush(build);
    }

    @Transactional(readOnly = true)
    @Override
    public RoomFacility getById(String id) {
        return roomFacilityRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase())
        );
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RoomFacility update(RoomFacilityRequest updateRoomFacility) {
        validator.validate(updateRoomFacility);
        RoomFacility byId = getById(updateRoomFacility.getRoomFacilityId());
        byId.setRoomFacilityName(updateRoomFacility.getRoomFacilityName());
        return roomFacilityRepository.saveAndFlush(byId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        RoomFacility byId = getById(id);
        roomFacilityRepository.delete(byId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RoomFacilityResponse createAndGetResponse(RoomFacilityRequest updateRoomFacility) {
        RoomFacility andGet = createAndGet(updateRoomFacility);
        return roomFacilityMapper.toResponse(andGet);
    }

    @Transactional(readOnly = true)
    @Override
    public RoomFacilityResponse getByIdResponse(String id) {
        RoomFacility byId = getById(id);
        return roomFacilityMapper.toResponse(byId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RoomFacilityResponse updateResponse(RoomFacilityRequest updateRoomFacility) {
        RoomFacility update = update(updateRoomFacility);
        return roomFacilityMapper.toResponse(update);
    }
}
