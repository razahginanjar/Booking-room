package com.enigma.challengebookingroom.repository;

import java.time.LocalDate;
import java.util.List;

import com.enigma.challengebookingroom.constant.ConstantReservationStatus;
import com.enigma.challengebookingroom.dto.response.GetReservationStatusResponse;
import com.enigma.challengebookingroom.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.enigma.challengebookingroom.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
    List<Reservation> findAllByReservationStatus(ConstantReservationStatus status);
    List<Reservation> findAllByEmployee(Employee employee);

//    @Query(value = "SELECT res.reservationId, u.employeeName, " +
//            "r.roomType, e.equipmentName, res.startTime, res.endTime " +
//            "FROM Reservation res " +
//            "JOIN Room r ON res.room = r.roomId " +
//            "JOIN Equipment e ON res.equipments " +
//            "JOIN Employee u ON res.employee = u.employeeId " +
//            "WHERE :date BETWEEN res.startTime AND res.endTime")
//    SELECT
//    re.reservationid,
//    r.roomType AS RoomName,
//    e.equipmentName AS EquipmentName,
//    res.startdate,
//    res.enddate
//            FROM
//    Reservation res
//    JOIN
//    Room r ON res.room_id = r.roomId
//            JOIN
//    Reservation_Equipment re ON res.reservationId = re.reservationId
//            JOIN
//    Equipment e ON re.equipment_id = e.equipmentId
//            WHERE
//    '2024-09-05' BETWEEN res.startDate AND res.endDate;

    //@Query(value = "select res from Reservation res where :date BETWEEN res.startTime AND res.endTime")
    @Query(value = "SELECT res " +
            "FROM Reservation res " +
            "WHERE :date BETWEEN res.startTime AND res.endTime")
    List<Reservation> findStatusReservation(@Param("date") LocalDate date);

//    @Query(value = "SELECT res FROM Reservation res WHERE res.room.roomId = :id " +
//            "AND ((:start BETWEEN res.startTime AND res.endTime) " +
//            "OR (:end BETWEEN res.startTime AND res.endTime) " +
//            "OR (res.startTime BETWEEN :start AND :end) " +
//            "OR (res.endTime BETWEEN :start AND :end))")

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
    void updateReservationStatus( @Param("id") String id, @Param("status") ConstantReservationStatus status);
}