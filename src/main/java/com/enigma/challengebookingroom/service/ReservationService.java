package com.enigma.challengebookingroom.service;

import com.enigma.challengebookingroom.constant.ConstantReservationStatus;
import com.enigma.challengebookingroom.dto.request.ReservationRequest;
import com.enigma.challengebookingroom.dto.request.UpdateReservationByUser;
import com.enigma.challengebookingroom.dto.request.UpdateReservationRequestByAdmin;
import com.enigma.challengebookingroom.dto.response.ReservationResponse;
import com.enigma.challengebookingroom.entity.Reservation;

import java.util.List;

public interface ReservationService {
    ReservationResponse create(ReservationRequest reservation);
    // List<ReservationResponse> getAll();
    List<ReservationResponse> getAllByStatus(ConstantReservationStatus status);
    ReservationResponse getById(String id);
    Reservation getReservationById(String id);
    ReservationResponse update(UpdateReservationByUser reservation);
    ReservationResponse updateByAdmin(UpdateReservationRequestByAdmin reservation);
    // sengaja ga ku tambahin delete karena ini kan transaksi masa di delete?
}
