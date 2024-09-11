package com.enigma.challengebookingroom.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportResponse {
    private String employeeName;
    private String roomType;
    private List<String> equipments;
    private LocalDate reserveDate;
    private LocalDate startTime;
    private LocalDate endTime;
    private String reservationStatus;
    private String reservationDescription;
}
