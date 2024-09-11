package com.enigma.challengebookingroom.entity;

import com.enigma.challengebookingroom.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

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