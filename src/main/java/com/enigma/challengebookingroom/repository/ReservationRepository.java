package com.enigma.challengebookingroom.repository;

import java.util.List;
import java.util.Optional;

import com.enigma.challengebookingroom.constant.ConstantReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enigma.challengebookingroom.constant.ConstantReservationAction;
import com.enigma.challengebookingroom.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
    List<Reservation> findAllByReservationStatus(ConstantReservationStatus status);
}