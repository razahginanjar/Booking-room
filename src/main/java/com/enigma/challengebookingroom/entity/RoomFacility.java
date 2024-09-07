package com.enigma.challengebookingroom.entity;

import com.enigma.challengebookingroom.constant.ConstantTable;

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
@Table(name = ConstantTable.ROOM_FACILITY)
@Builder
public class RoomFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "room_facility_id", nullable = false)
    private String roomFacilityId;

    @Column(name = "room_facility")
    private String roomFacilityName;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}