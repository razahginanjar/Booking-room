package com.enigma.challengebookingroom.entity;

import java.util.List;

import com.enigma.challengebookingroom.constant.ConstantRole;
import com.enigma.challengebookingroom.constant.ConstantTable;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<User> users;

}
