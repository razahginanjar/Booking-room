package com.enigma.challengebookingroom.dto.response;

import com.enigma.challengebookingroom.constant.ConstantReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponse {
    private String typeRoom;
    private String startDate;
    private String endDate;
    private String description;
    private String employeeName;
    private String division;
    private List<EquipmentResponse> equipments;
    private String reserveDate;
    private ConstantReservationStatus reservationStatus;
}
