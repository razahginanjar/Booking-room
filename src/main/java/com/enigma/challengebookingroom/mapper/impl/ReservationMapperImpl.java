package com.enigma.challengebookingroom.mapper.impl;

import com.enigma.challengebookingroom.dto.response.*;
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

import java.util.Objects;

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
        ReservationResponse build = ReservationResponse.builder()
                .reservationId(reservation.getReservationId())
                .employee(mapToEmployeeResponse(reservation.getEmployee()))
                .room(mapToRoomResponse(reservation.getRoom()))
                .reserveDate(reservation.getReserveDate())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .reservationStatus(reservation.getReservationStatus())
                .reservationDescription(reservation.getReservationDescription())
                .build();
        if(Objects.nonNull(reservation.getEquipments()))
         {
             build.setEquipments(reservation.getEquipments().stream()
                     .map(this::mapToEquipmentResponse)
                     .toList());
         }

         return build;
    }

    @Override
    public GetReservationStatusResponse getResponseStatus(Reservation reservation) {
        GetReservationStatusResponse build = GetReservationStatusResponse.builder()
                .reservationId(reservation.getReservationId())
                .employee(reservation.getEmployee().getEmployeeName())
                .room(reservation.getRoom().getRoomType())
                .startDate(reservation.getStartTime())
                .endDate(reservation.getEndTime())
                .build();
        if(Objects.nonNull(reservation.getEquipments()))
        {
            build.setEquipments(reservation.getEquipments().stream()
                    .map(Equipment::getEquipmentName)
                    .toList());
        }
        return build;
    }
}
