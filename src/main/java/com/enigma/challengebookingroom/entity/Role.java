package com.enigma.challengebookingroom.entity;

import com.enigma.challengebookingroom.constant.ConstantRole;
import com.enigma.challengebookingroom.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.ROLE)
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "role_id", nullable = false, updatable = false, unique = true)
    private String roleId;

    @Column(name = "role", nullable = false, updatable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private ConstantRole constantRole;

//    @OneToMany(mappedBy = "roles", fetch = FetchType.EAGER)
//    @JsonManagedReference
//    private List<User> users;

}
