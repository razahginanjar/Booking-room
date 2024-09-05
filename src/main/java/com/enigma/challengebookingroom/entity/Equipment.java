package com.enigma.challengebookingroom.entity;

import com.enigma.challengebookingroom.constant.ConstantEquipmentStatus;
import com.enigma.challengebookingroom.constant.ConstantTable;
import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name = ConstantTable.EQUIPMENT)
@Builder
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private String equipmentId;

    @Column(name = "equipment_name", nullable = false, unique = true)
    private String equipmentName;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ConstantEquipmentStatus equipmentStatus;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

}