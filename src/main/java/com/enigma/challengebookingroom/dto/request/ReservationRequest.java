package com.enigma.challengebookingroom.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.enigma.challengebookingroom.entity.Reservation}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReservationRequest implements Serializable {
    String reservationId;
    LocalDateTime reserveDate;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String reservationDescription;
    String actionReason;
}