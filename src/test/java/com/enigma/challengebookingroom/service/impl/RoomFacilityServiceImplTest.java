package com.enigma.challengebookingroom.service.impl;

import com.enigma.challengebookingroom.dto.request.RoomFacilityRequest;
import com.enigma.challengebookingroom.dto.response.RoomFacilityResponse;
import com.enigma.challengebookingroom.entity.RoomFacility;
import com.enigma.challengebookingroom.mapper.RoomFacilityMapper;
import com.enigma.challengebookingroom.repository.RoomFacilityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RoomFacilityServiceImplTest {

    @Mock
    private RoomFacilityRepository roomFacilityRepository;

    @Mock
    private RoomFacilityMapper roomFacilityMapper;

    @InjectMocks
    private RoomFacilityServiceImpl roomFacilityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAndGet_ShouldReturnSavedRoomFacility() {
        // Arrange
        RoomFacilityRequest request = new RoomFacilityRequest("1", "Projector");

        RoomFacility roomFacility = RoomFacility.builder()
                .roomFacilityId("1")
                .roomFacilityName("Projector")
                .build();

        when(roomFacilityRepository.saveAndFlush(any(RoomFacility.class))).thenReturn(roomFacility);

        // Act
        RoomFacility savedRoomFacility = roomFacilityService.createAndGet(request);

        // Assert
        assertEquals("Projector", savedRoomFacility.getRoomFacilityName());
        verify(roomFacilityRepository, times(1)).saveAndFlush(any(RoomFacility.class));
    }

    @Test
    void testGetById_ShouldReturnRoomFacility_WhenFound() {
        // Arrange
        String roomFacilityId = "1";
        RoomFacility roomFacility = RoomFacility.builder()
                .roomFacilityId(roomFacilityId)
                .roomFacilityName("Projector")
                .build();

        when(roomFacilityRepository.findById(roomFacilityId)).thenReturn(Optional.of(roomFacility));

        // Act
        RoomFacility foundRoomFacility = roomFacilityService.getById(roomFacilityId);

        // Assert
        assertEquals(roomFacilityId, foundRoomFacility.getRoomFacilityId());
        assertEquals("Projector", foundRoomFacility.getRoomFacilityName());
        verify(roomFacilityRepository, times(1)).findById(roomFacilityId);
    }

    @Test
    void testGetById_ShouldThrowException_WhenNotFound() {
        // Arrange
        String roomFacilityId = "1";
        when(roomFacilityRepository.findById(roomFacilityId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> roomFacilityService.getById(roomFacilityId));
        verify(roomFacilityRepository, times(1)).findById(roomFacilityId);
    }

    @Test
    void testUpdate_ShouldUpdateAndReturnRoomFacility() {
        // Arrange
        String roomFacilityId = "1";
        RoomFacilityRequest request = new RoomFacilityRequest(roomFacilityId, "Updated Projector");

        RoomFacility existingRoomFacility = RoomFacility.builder()
                .roomFacilityId(roomFacilityId)
                .roomFacilityName("Projector")
                .build();

        RoomFacility updatedRoomFacility = RoomFacility.builder()
                .roomFacilityId(roomFacilityId)
                .roomFacilityName("Updated Projector")
                .build();

        when(roomFacilityRepository.findById(roomFacilityId)).thenReturn(Optional.of(existingRoomFacility));
        when(roomFacilityRepository.saveAndFlush(any(RoomFacility.class))).thenReturn(updatedRoomFacility);

        // Act
        RoomFacility result = roomFacilityService.update(request);

        // Assert
        assertEquals("Updated Projector", result.getRoomFacilityName());
        verify(roomFacilityRepository, times(1)).findById(roomFacilityId);
        verify(roomFacilityRepository, times(1)).saveAndFlush(any(RoomFacility.class));
    }

    @Test
    void testDelete_ShouldRemoveRoomFacility_WhenFound() {
        // Arrange
        String roomFacilityId = "1";
        RoomFacility roomFacility = RoomFacility.builder()
                .roomFacilityId(roomFacilityId)
                .roomFacilityName("Projector")
                .build();

        when(roomFacilityRepository.findById(roomFacilityId)).thenReturn(Optional.of(roomFacility));

        // Act
        roomFacilityService.delete(roomFacilityId);

        // Assert
        verify(roomFacilityRepository, times(1)).delete(roomFacility);
    }

    @Test
    void testCreateAndGetResponse_ShouldReturnRoomFacilityResponse() {
        // Arrange
        RoomFacilityRequest request = new RoomFacilityRequest("1", "Projector");

        RoomFacility roomFacility = RoomFacility.builder()
                .roomFacilityId("1")
                .roomFacilityName("Projector")
                .build();

        RoomFacilityResponse response = new RoomFacilityResponse();

        when(roomFacilityRepository.saveAndFlush(any(RoomFacility.class))).thenReturn(roomFacility);
        when(roomFacilityMapper.toResponse(roomFacility)).thenReturn(response);

        // Act
        RoomFacilityResponse result = roomFacilityService.createAndGetResponse(request);

        // Assert
        assertEquals(response.getRoomFacilityName(), result.getRoomFacilityName());
        verify(roomFacilityRepository, times(1)).saveAndFlush(any(RoomFacility.class));
        verify(roomFacilityMapper, times(1)).toResponse(roomFacility);
    }

    @Test
    void testGetByIdResponse_ShouldReturnRoomFacilityResponse() {
        // Arrange
        String roomFacilityId = "1";
        RoomFacility roomFacility = RoomFacility.builder()
                .roomFacilityId(roomFacilityId)
                .roomFacilityName("Projector")
                .build();

        RoomFacilityResponse response = new RoomFacilityResponse();

        when(roomFacilityRepository.findById(roomFacilityId)).thenReturn(Optional.of(roomFacility));
        when(roomFacilityMapper.toResponse(roomFacility)).thenReturn(response);

        // Act
        RoomFacilityResponse result = roomFacilityService.getByIdResponse(roomFacilityId);

        // Assert
        assertEquals(response.getRoomFacilityName(), result.getRoomFacilityName());
        verify(roomFacilityRepository, times(1)).findById(roomFacilityId);
        verify(roomFacilityMapper, times(1)).toResponse(roomFacility);
    }

    @Test
    void testUpdateResponse_ShouldReturnUpdatedRoomFacilityResponse() {
        // Arrange
        String roomFacilityId = "1";
        RoomFacilityRequest request = new RoomFacilityRequest(roomFacilityId, "Updated Projector");

        RoomFacility existingRoomFacility = RoomFacility.builder()
                .roomFacilityId(roomFacilityId)
                .roomFacilityName("Projector")
                .build();

        RoomFacility updatedRoomFacility = RoomFacility.builder()
                .roomFacilityId(roomFacilityId)
                .roomFacilityName("Updated Projector")
                .build();

        RoomFacilityResponse response = new RoomFacilityResponse();

        when(roomFacilityRepository.findById(roomFacilityId)).thenReturn(Optional.of(existingRoomFacility));
        when(roomFacilityRepository.saveAndFlush(any(RoomFacility.class))).thenReturn(updatedRoomFacility);
        when(roomFacilityMapper.toResponse(updatedRoomFacility)).thenReturn(response);

        // Act
        RoomFacilityResponse result = roomFacilityService.updateResponse(request);

        // Assert
        assertEquals(response.getRoomFacilityName(), result.getRoomFacilityName());
        verify(roomFacilityRepository, times(1)).findById(roomFacilityId);
        verify(roomFacilityRepository, times(1)).saveAndFlush(any(RoomFacility.class));
        verify(roomFacilityMapper, times(1)).toResponse(updatedRoomFacility);
    }
}
