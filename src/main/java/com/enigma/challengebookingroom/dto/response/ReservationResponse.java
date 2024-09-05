package com.enigma.challengebookingroom.dto.response;

import com.enigma.challengebookingroom.constant.ConstantReservationStatus;
import com.enigma.challengebookingroom.entity.Employee;
import com.enigma.challengebookingroom.entity.Equipment;
import com.enigma.challengebookingroom.entity.Room;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ReservationResponse {
    private final String reservationId;
    private final Employee employee;
    private final Room room;
    private final LocalDate reserveDate;
    private final LocalDate startTime;
    private final LocalDate endTime;
    private final ConstantReservationStatus reservationStatus;
    private final String reservationDescription;
    private final List<Equipment> equipments;
}