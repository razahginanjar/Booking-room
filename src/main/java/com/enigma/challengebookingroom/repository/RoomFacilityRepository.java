package com.enigma.challengebookingroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enigma.challengebookingroom.entity.RoomFacility;

@Repository
public interface RoomFacilityRepository extends JpaRepository<RoomFacility, String> {
}