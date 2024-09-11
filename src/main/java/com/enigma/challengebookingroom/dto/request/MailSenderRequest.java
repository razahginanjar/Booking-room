package com.enigma.challengebookingroom.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailSenderRequest {
    private String idReservation;
    private String employeeName;
    private String roomType;
    private String startDate;
    private String endDate;
    private List<String> equipment;
}
