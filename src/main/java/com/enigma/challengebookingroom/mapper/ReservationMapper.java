package com.enigma.challengebookingroom.mapper;

import com.enigma.challengebookingroom.dto.response.GetReservationStatusResponse;
import com.enigma.challengebookingroom.dto.response.ReservationResponse;
import com.enigma.challengebookingroom.entity.Reservation;

public interface ReservationMapper {
    ReservationResponse toResponse(Reservation reservation);

    GetReservationStatusResponse getResponseStatus(Reservation reservation);
}
