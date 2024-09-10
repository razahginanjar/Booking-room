package com.enigma.challengebookingroom.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetReservationStatusResponse {
    private String reservationId;
    private String employee;
    private String room;
    private List<String> equipments;
    private LocalDate startDate;
    private LocalDate endDate;
}
