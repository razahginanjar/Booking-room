package com.enigma.challengebookingroom.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetReservationStatusResponse {
    private String reservationId;
    private String employee;
    private String room;
    private String equipment;
    private LocalDate startDate;
    private LocalDate endDate;
}
