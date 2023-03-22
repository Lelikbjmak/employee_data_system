package com.innowise.employeedatasystem.repo;

import com.innowise.employeedatasystem.entity.Role;
import com.innowise.employeedatasystem.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRole(RoleEnum role);

}
