package com.enigma.challengebookingroom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enigma.challengebookingroom.constant.ConstantEquipmentStatus;
import com.enigma.challengebookingroom.entity.EquipmentStatus;


@Repository
public interface EquipmentStatusRepository extends JpaRepository<EquipmentStatus, String> {
    Optional<EquipmentStatus> findAllByEquipmentStatus(ConstantEquipmentStatus constantEquipmentStatus);
}