package com.enigma.challengebookingroom.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddRoleRequest {
    private String username;
    private String role;
}
