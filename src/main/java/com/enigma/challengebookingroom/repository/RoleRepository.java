package com.enigma.challengebookingroom.repository;

import com.enigma.challengebookingroom.constant.ConstantRole;
import com.enigma.challengebookingroom.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByConstantRole(ConstantRole role);

    Boolean existsByConstantRole(ConstantRole constantRole);
}