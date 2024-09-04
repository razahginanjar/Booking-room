package com.enigma.challengebookingroom.entity;

import com.enigma.challengebookingroom.constant.ConstantTable;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    @Column(name = "equipment_id", nullable = false, updatable = false, unique = true)
    private String equipmentId;

    @Column(name = "equipment_name", nullable = false, unique = true)
    private String equipmentName;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "equipment_status_id")
    private EquipmentStatus equipmentStatus;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

}