package com.enigma.challengebookingroom.entity;

import com.enigma.challengebookingroom.constant.ConstantTable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = ConstantTable.ROOM_FACILITY)
@Builder
public class RoomFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "room_facility_id", nullable = false)
    private String roomFacilityId;

    @Column(name = "room_facility")
    private String roomFacilityName;

}