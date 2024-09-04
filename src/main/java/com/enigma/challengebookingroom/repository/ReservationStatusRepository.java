package com.enigma.challengebookingroom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enigma.challengebookingroom.constant.ConstantReservationStatus;
import com.enigma.challengebookingroom.entity.ReservationStatus;

@Repository
public interface ReservationStatusRepository extends JpaRepository<ReservationStatus, String> {
    Optional<ReservationStatus> findAllByReservationStatus(ConstantReservationStatus constantReservationStatus);
}