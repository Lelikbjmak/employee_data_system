package com.innowise.employeedatasystem.mapper;

import com.innowise.employeedatasystem.entity.Role;
import com.innowise.employeedatasystem.entity.RoleEnum;
import com.innowise.employeedatasystem.repo.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleSetMapper {

    private final RoleRepository roleRepository;

    public Set<Role> toRoleEntitySet(Set<String> roles) {
        return roles.stream()
                .map(role -> roleRepository
                        .findByRole(RoleEnum.valueOf(role))
                        .orElseThrow(RuntimeException::new))
                .collect(Collectors.toSet());
    }

    public Set<String> toRoleDtoSet(Set<Role> roles) {
        return roles.stream()
                .map(role -> role.getRole().name())
                .collect(Collectors.toSet());
    }

}
