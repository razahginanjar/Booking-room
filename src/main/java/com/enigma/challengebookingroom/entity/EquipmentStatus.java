package com.enigma.challengebookingroom.entity;

import com.enigma.challengebookingroom.constant.ConstantEquipmentStatus;
import com.enigma.challengebookingroom.constant.ConstantTable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = ConstantTable.EQUIPMENT_STATUS)
@Builder
public class EquipmentStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "equipment_status_id", nullable = false, updatable = false, unique = true)
    private String equipmentStatusId;

    @Column(name = "equipment_status", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private ConstantEquipmentStatus equipmentStatusName;

}
