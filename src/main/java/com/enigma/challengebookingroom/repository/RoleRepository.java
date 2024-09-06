package com.enigma.challengebookingroom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enigma.challengebookingroom.constant.ConstantRole;
import com.enigma.challengebookingroom.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findAllByConstantRole(ConstantRole role);
}