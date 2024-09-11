package com.enigma.challengebookingroom.repository;

import com.enigma.challengebookingroom.constant.ConstantReservationStatus;
import com.enigma.challengebookingroom.entity.Employee;
import com.enigma.challengebookingroom.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
    List<Reservation> findAllByReservationStatus(ConstantReservationStatus status);

    List<Reservation> findAllByEmployee(Employee employee);

    @Query(value = "SELECT res FROM Reservation res WHERE :date BETWEEN res.startTime AND res.endTime")
    List<Reservation> findStatusReservation(@Param("date") LocalDate date);

    @Query("SELECT res FROM Reservation res WHERE res.room.roomId = :id " +
            "AND ((:start BETWEEN res.startTime AND res.endTime) " +
            "OR (:end BETWEEN res.startTime AND res.endTime) " +
            "OR (res.startTime BETWEEN :start AND :end) " +
            "OR (res.endTime BETWEEN :start AND :end))")
    List<Reservation> findAvailRoom(
            @Param("id") String id,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end);

    @Modifying
    @Query(value = "UPDATE Reservation res SET res.reservationStatus = :status WHERE res.reservationId = :id")
    void updateReservationStatus(@Param("id") String id, @Param("status") ConstantReservationStatus status);
}