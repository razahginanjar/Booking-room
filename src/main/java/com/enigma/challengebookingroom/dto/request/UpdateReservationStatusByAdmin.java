package com.enigma.challengebookingroom.dto.request;

import com.enigma.challengebookingroom.constant.ConstantReservationStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateReservationStatusByAdmin {
    private String idReservation;
    private ConstantReservationStatus reservationStatus;
}
