package com.enigma.challengebookingroom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enigma.challengebookingroom.constant.ConstantReservationAction;
import com.enigma.challengebookingroom.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
    Optional<Reservation> findAllByReservationAction(ConstantReservationAction constantReservationAction);
}