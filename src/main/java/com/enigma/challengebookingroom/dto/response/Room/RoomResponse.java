package com.enigma.challengebookingroom.dto.response.Room;

import com.enigma.challengebookingroom.dto.response.RoomFacility.RoomFacilityResponse;
import com.enigma.challengebookingroom.entity.RoomFacility;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomResponse {
    private String roomType;

    private Integer roomCapacity;

    private List<RoomFacilityResponse> roomFacilities;

    private Boolean isAvailable;

}
