package com.enigma.challengebookingroom.service;

import com.enigma.challengebookingroom.constant.ConstantReservationStatus;
import com.enigma.challengebookingroom.dto.request.InsertDateRequest;
import com.enigma.challengebookingroom.dto.request.ReservationRequest;
import com.enigma.challengebookingroom.dto.request.UpdateReservationByAdmin;
import com.enigma.challengebookingroom.dto.request.UpdateReservationStatusByAdmin;
import com.enigma.challengebookingroom.dto.response.GetReservationStatusResponse;
import com.enigma.challengebookingroom.dto.response.ReservationResponse;
import com.enigma.challengebookingroom.entity.Reservation;

import java.util.List;

public interface ReservationService {
    ReservationResponse create(ReservationRequest reservation);
    // List<ReservationResponse> getAll();
    List<ReservationResponse> getAllByStatus(ConstantReservationStatus status);
    ReservationResponse getById(String id);
    Reservation getReservationById(String id);
    ReservationResponse update(UpdateReservationByAdmin reservation);
    ReservationResponse updateByAdmin(UpdateReservationStatusByAdmin reservation);
    // sengaja ga ku tambahin delete karena ini kan transaksi masa di delete?

    List<GetReservationStatusResponse> getStatusReservations(InsertDateRequest date);
    List<Reservation> historyOfCustomer();
}
