package com.enigma.challengebookingroom.dto.request;

import com.enigma.challengebookingroom.constant.ConstantRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.enigma.challengebookingroom.entity.Role}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleRequest implements Serializable {
    String roleId;
    ConstantRole constantRole;
}