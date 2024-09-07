package com.enigma.challengebookingroom.repository;

import java.util.List;

import com.enigma.challengebookingroom.constant.ConstantReservationStatus;
import com.enigma.challengebookingroom.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enigma.challengebookingroom.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
    List<Reservation> findAllByReservationStatus(ConstantReservationStatus status);
    List<Reservation> findAllByEmployee(Employee employee);
}