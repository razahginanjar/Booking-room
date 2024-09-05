package com.enigma.challengebookingroom.entity;

import com.enigma.challengebookingroom.constant.ConstantReservationStatus;
import com.enigma.challengebookingroom.constant.ConstantTable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.RESERVATION)
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private String reservationId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "reserve_date", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reserveDate;

    @Column(name = "start_time", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startTime;

    @Column(name = "end_time", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ConstantReservationStatus reservationStatus;

    @Column(name = "reservation_description", nullable = false, updatable = false)
    private String reservationDescription;

    @OneToMany(mappedBy = "reservation", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Equipment> equipments;

    @PrePersist
    protected void onCreate() {
        this.reserveDate = LocalDate.now();
        this.startTime = LocalDate.now().plusDays(1);
        this.endTime = LocalDate.now().plusDays(1);
    }

}