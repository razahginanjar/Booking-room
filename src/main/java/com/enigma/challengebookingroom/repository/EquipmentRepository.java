package com.enigma.challengebookingroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enigma.challengebookingroom.entity.Equipment;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, String> {
}