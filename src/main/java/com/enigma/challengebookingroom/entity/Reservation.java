package com.enigma.challengebookingroom.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.enigma.challengebookingroom.constant.ConstantReservationStatus;
import com.enigma.challengebookingroom.constant.ConstantTable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reserveDate;

    @Column(name = "start_time", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

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
        this.reserveDate = LocalDateTime.now();
        this.startTime = LocalDateTime.now().plusDays(1);
        this.endTime = LocalDateTime.now().plusDays(1).plusHours(4);
    }

}