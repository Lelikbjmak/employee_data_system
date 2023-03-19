package com.innowise.employeedatasystem.mapper;

import com.innowise.employeedatasystem.entity.Role;
import com.innowise.employeedatasystem.entity.RoleEnum;
import com.innowise.employeedatasystem.exception.RoleIsNotFoundException;
import com.innowise.employeedatasystem.serviceimpl.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleSetMapper {

    private static final String ROLE_IS_NOT_FOUND_EXCEPTION_MESSAGE = "Role is not found.";

    private final RoleServiceImpl roleService;

    public Set<Role> toRoleEntitySet(Set<String> roles) {

        Set<Role> roleSet = new HashSet<>(roles.size());

        roles.forEach(role -> {
            try {
                roleSet.add(roleService.findByRole(RoleEnum.valueOf(role)));
            } catch (IllegalArgumentException e) {
                throw new RoleIsNotFoundException(ROLE_IS_NOT_FOUND_EXCEPTION_MESSAGE,
                        Instant.now(), Map.of("Role", role));
            }
        });

        return roleSet;
    }

    public Set<String> toRoleDtoSet(Set<Role> roles) {
        return roles.stream()
                .map(role -> role.getRole().name())
                .collect(Collectors.toSet());
    }

}
