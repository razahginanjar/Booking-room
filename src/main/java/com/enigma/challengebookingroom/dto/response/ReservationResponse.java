package com.enigma.challengebookingroom.dto.response;

import com.enigma.challengebookingroom.constant.ConstantReservationStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {
    private String reservationId;
    private EmployeeResponse employee;
    private RoomResponse room;
    private EquipmentResponse equipment;
    private LocalDate reserveDate;
    private LocalDate startTime;
    private LocalDate endTime;
    private ConstantReservationStatus reservationStatus;
    private String reservationDescription;
    private List<EquipmentResponse> equipments;
}