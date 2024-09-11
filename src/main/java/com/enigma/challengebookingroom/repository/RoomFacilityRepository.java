package com.enigma.challengebookingroom.repository;

import com.enigma.challengebookingroom.entity.RoomFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomFacilityRepository extends JpaRepository<RoomFacility, String> {
}