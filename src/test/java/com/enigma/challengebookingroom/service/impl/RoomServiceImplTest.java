package com.enigma.challengebookingroom.service.impl;

import com.enigma.challengebookingroom.dto.request.RoomRequest;
import com.enigma.challengebookingroom.dto.response.RoomResponse;
import com.enigma.challengebookingroom.entity.Room;
import com.enigma.challengebookingroom.mapper.RoomMapper;
import com.enigma.challengebookingroom.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;

class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomMapper roomMapper;

    @InjectMocks
    private RoomServiceImpl roomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById_ShouldReturnRoom_WhenFound() {
        // Arrange
        String roomId = "1";
        Room room = Room.builder()
                .roomId(roomId)
                .roomCapacity(20)
                .roomType("Conference")
                .isAvailable(true)
                .build();

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));

        // Act
        Room foundRoom = roomService.getById(roomId);

        // Assert
        assertEquals(roomId, foundRoom.getRoomId());
        assertEquals(20, foundRoom.getRoomCapacity());
        assertEquals("Conference", foundRoom.getRoomType());
        assertTrue(foundRoom.getIsAvailable());
        verify(roomRepository, times(1)).findById(roomId);
    }

    @Test
    void testGetById_ShouldThrowException_WhenNotFound() {
        // Arrange
        String roomId = "1";
        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> roomService.getById(roomId));
        verify(roomRepository, times(1)).findById(roomId);
    }

    @Test
    void testCreateAndGet_ShouldReturnSavedRoom() {
        // Arrange
        RoomRequest request = new RoomRequest("1", "Conference", 20,true);

        Room room = Room.builder()
                .roomId("1")
                .roomCapacity(20)
                .roomType("Conference")
                .isAvailable(true)
                .build();

        when(roomRepository.saveAndFlush(any(Room.class))).thenReturn(room);

        // Act
        Room savedRoom = roomService.createAndGet(request);

        // Assert
        assertEquals("1", savedRoom.getRoomId());
        assertEquals(20, savedRoom.getRoomCapacity());
        assertEquals("Conference", savedRoom.getRoomType());
        assertTrue(savedRoom.getIsAvailable());
        verify(roomRepository, times(1)).saveAndFlush(any(Room.class));
    }

    @Test
    void testUpdateAndGet_ShouldUpdateAndReturnRoom() {
        // Arrange
        String roomId = "1";
        RoomRequest request = new RoomRequest(roomId, "Meeting", 30, false);

        Room existingRoom = Room.builder()
                .roomId(roomId)
                .roomCapacity(20)
                .roomType("Conference")
                .isAvailable(true)
                .build();

        Room updatedRoom = Room.builder()
                .roomId(roomId)
                .roomCapacity(30)
                .roomType("Meeting")
                .isAvailable(false)
                .build();

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(existingRoom));
        when(roomRepository.saveAndFlush(any(Room.class))).thenReturn(updatedRoom);

        // Act
        Room result = roomService.updateAndGet(request);

        // Assert
        assertEquals(30, result.getRoomCapacity());
        assertEquals("Meeting", result.getRoomType());
        assertFalse(result.getIsAvailable());
        verify(roomRepository, times(1)).findById(roomId);
        verify(roomRepository, times(1)).saveAndFlush(any(Room.class));
    }

    @Test
    void testGetAll_ShouldReturnListOfRooms() {
        // Arrange
        List<Room> rooms = new ArrayList<>();
        rooms.add(Room.builder().roomId("1").roomCapacity(20).roomType("Conference").isAvailable(true).build());
        rooms.add(Room.builder().roomId("2").roomCapacity(30).roomType("Meeting").isAvailable(false).build());

        when(roomRepository.findAll()).thenReturn(rooms);

        // Act
        List<Room> result = roomService.getAll();

        // Assert
        assertEquals(2, result.size());
        verify(roomRepository, times(1)).findAll();
    }

    @Test
    void testGetAllByAvailable_ShouldReturnListOfAvailableRooms() {
        // Arrange
        Boolean isAvailable = true;
        List<Room> rooms = new ArrayList<>();
        rooms.add(Room.builder().roomId("1").roomCapacity(20).roomType("Conference").isAvailable(true).build());

        when(roomRepository.findAllByIsAvailable(isAvailable)).thenReturn(rooms);

        // Act
        List<Room> result = roomService.getAllByAvailable(isAvailable);

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.get(0).getIsAvailable());
        verify(roomRepository, times(1)).findAllByIsAvailable(isAvailable);
    }

    @Test
    void testDeleteRoom_ShouldRemoveRoom_WhenFound() {
        // Arrange
        String roomId = "1";
        Room room = Room.builder()
                .roomId(roomId)
                .roomCapacity(20)
                .roomType("Conference")
                .isAvailable(true)
                .build();

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));

        // Act
        roomService.deleteRoom(roomId);

        // Assert
        verify(roomRepository, times(1)).delete(room);
    }

    @Test
    void testGetByIdResponse_ShouldReturnRoomResponse() {
        // Arrange
        String roomId = "1";
        Room room = Room.builder()
                .roomId(roomId)
                .roomCapacity(20)
                .roomType("Conference")
                .isAvailable(true)
                .build();

        RoomResponse response = new RoomResponse(roomId, 20, null, true);

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
        when(roomMapper.toResponse(room)).thenReturn(response);

        // Act
        RoomResponse result = roomService.getByIdResponse(roomId);

        // Assert
        assertEquals(response.getRoomType(), result.getRoomType());
        assertEquals(response.getRoomCapacity(), result.getRoomCapacity());
        assertEquals(response.getRoomType(), result.getRoomType());
        assertEquals(response.getIsAvailable(), result.getIsAvailable());
        verify(roomRepository, times(1)).findById(roomId);
        verify(roomMapper, times(1)).toResponse(room);
    }

    @Test
    void testCreateAndGetResponse_ShouldReturnRoomResponse() {
        // Arrange
        RoomRequest request = new RoomRequest("1", "Conference", 20, true);

        Room room = Room.builder()
                .roomId("1")
                .roomCapacity(20)
                .roomType("Conference")
                .isAvailable(true)
                .build();

        RoomResponse response = new RoomResponse("1", 20, null, true);

        when(roomRepository.saveAndFlush(any(Room.class))).thenReturn(room);
        when(roomMapper.toResponse(room)).thenReturn(response);

        // Act
        RoomResponse result = roomService.createAndGetResponse(request);

        // Assert
        assertEquals(response.getRoomType(), result.getRoomType());
        assertEquals(response.getRoomCapacity(), result.getRoomCapacity());
        assertEquals(response.getRoomType(), result.getRoomType());
        assertEquals(response.getIsAvailable(), result.getIsAvailable());
        verify(roomRepository, times(1)).saveAndFlush(any(Room.class));
        verify(roomMapper, times(1)).toResponse(room);
    }

    @Test
    void testUpdateAndGetResponse_ShouldReturnUpdatedRoomResponse() {
        // Arrange
        String roomId = "1";
        RoomRequest request = new RoomRequest(roomId, "Meeting", 30, false);

        Room existingRoom = Room.builder()
                .roomId(roomId)
                .roomCapacity(20)
                .roomType("Conference")
                .isAvailable(true)
                .build();

        Room updatedRoom = Room.builder()
                .roomId(roomId)
                .roomCapacity(30)
                .roomType("Meeting")
                .isAvailable(false)
                .build();

        RoomResponse response = new RoomResponse(roomId, 30, null, false);

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(existingRoom));
        when(roomRepository.saveAndFlush(any(Room.class))).thenReturn(updatedRoom);
        when(roomMapper.toResponse(updatedRoom)).thenReturn(response);

        // Act
        RoomResponse result = roomService.updateAndGetResponse(request);

        // Assert
        assertEquals(response.getRoomType(), result.getRoomType());
        assertEquals(response.getRoomCapacity(), result.getRoomCapacity());
        assertEquals(response.getRoomType(), result.getRoomType());
        assertEquals(response.getIsAvailable(), result.getIsAvailable());
        verify(roomRepository, times(1)).findById(roomId);
        verify(roomRepository, times(1)).saveAndFlush(any(Room.class));
        verify(roomMapper, times(1)).toResponse(updatedRoom);
    }
}
