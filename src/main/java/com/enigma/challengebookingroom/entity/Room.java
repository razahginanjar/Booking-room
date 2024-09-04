package com.enigma.challengebookingroom.entity;

import java.util.List;

import com.enigma.challengebookingroom.constant.ConstantTable;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.ROOM)
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private String roomId;

    @Column(name = "room_type", nullable = false)
    private String roomType;

    @Column(name = "capacity", nullable = false)
    private String roomCapacity;

    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<RoomFacility> roomFacilities;

    @Column(name = "vacancy")
    private Boolean vacancy;

}