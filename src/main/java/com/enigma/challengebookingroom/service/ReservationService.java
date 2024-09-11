package com.enigma.challengebookingroom.service;

import com.enigma.challengebookingroom.constant.ConstantReservationStatus;
import com.enigma.challengebookingroom.dto.request.InsertDateRequest;
import com.enigma.challengebookingroom.dto.request.ReservationRequest;
import com.enigma.challengebookingroom.dto.request.UpdateReservationByAdmin;
import com.enigma.challengebookingroom.dto.response.GetReservationStatusResponse;
import com.enigma.challengebookingroom.dto.response.ReservationResponse;
import com.enigma.challengebookingroom.entity.Reservation;
import com.mailjet.client.errors.MailjetException;

import java.util.List;

public interface ReservationService {
    ReservationResponse create(ReservationRequest reservation) throws MailjetException;
    List<ReservationResponse> getAllByStatus(ConstantReservationStatus status);
    ReservationResponse getById(String id);
    Reservation getReservationById(String id);
    ReservationResponse update(UpdateReservationByAdmin reservation);
    void updateStatus(String id, ConstantReservationStatus status);

    List<GetReservationStatusResponse> getStatusReservations(InsertDateRequest date);
    List<ReservationResponse> historyOfCustomer();
    List<Reservation> getAll();
}
