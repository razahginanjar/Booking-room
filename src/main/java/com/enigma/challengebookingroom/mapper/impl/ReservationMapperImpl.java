package com.enigma.challengebookingroom.mapper.impl;

import com.enigma.challengebookingroom.dto.response.EmployeeResponse;
import com.enigma.challengebookingroom.dto.response.EquipmentResponse;
import com.enigma.challengebookingroom.dto.response.ReservationResponse;
import com.enigma.challengebookingroom.dto.response.RoomResponse;
import com.enigma.challengebookingroom.entity.Employee;
import com.enigma.challengebookingroom.entity.Equipment;
import com.enigma.challengebookingroom.entity.Reservation;
import com.enigma.challengebookingroom.entity.Room;
import com.enigma.challengebookingroom.mapper.EmployeeMapper;
import com.enigma.challengebookingroom.mapper.EquipmentMapper;
import com.enigma.challengebookingroom.mapper.ReservationMapper;
import com.enigma.challengebookingroom.mapper.RoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationMapperImpl implements ReservationMapper {
    private final EmployeeMapper employeeMapper;
    private final RoomMapper roomMapper;
    private final EquipmentMapper equipmentMapper;

     public EmployeeResponse mapToEmployeeResponse(Employee employee) {
         return employeeMapper.toResponse(employee);
     }

     public RoomResponse mapToRoomResponse(Room room) {
         return roomMapper.toResponse(room);
     }

     public EquipmentResponse mapToEquipmentResponse(Equipment equipment) {
         return equipmentMapper.toResponse(equipment);
     }

    @Override
    public ReservationResponse toResponse(Reservation reservation) {
        return ReservationResponse.builder()
                .reservationId(reservation.getReservationId())
                .employee(mapToEmployeeResponse(reservation.getEmployee()))
                .room(mapToRoomResponse(reservation.getRoom()))
                .reserveDate(reservation.getReserveDate())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .reservationStatus(reservation.getReservationStatus())
                .reservationDescription(reservation.getReservationDescriptionByUser())
                .equipments(reservation.getEquipments().stream()
                        .map(this::mapToEquipmentResponse)
                        .toList())
                .build();
    }
}
