package com.enigma.challengebookingroom.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.enigma.challengebookingroom.entity.Room}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomRequest implements Serializable {
    String roomId;
    String roomType;
    Integer roomCapacity;
    private Boolean isAvailable;
}