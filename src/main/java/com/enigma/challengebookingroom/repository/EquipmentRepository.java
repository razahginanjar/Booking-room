package com.enigma.challengebookingroom.repository;

import com.enigma.challengebookingroom.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, String> {
    Boolean existsByEquipmentName(String equipmentName);

    Optional<Equipment> findByEquipmentName(String equipmentName);
}