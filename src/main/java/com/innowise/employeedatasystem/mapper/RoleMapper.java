package com.innowise.employeedatasystem.mapper;

import com.innowise.employeedatasystem.entity.Role;
import com.innowise.employeedatasystem.entity.RoleEnum;
import com.innowise.employeedatasystem.exception.RoleIsNotFoundException;
import com.innowise.employeedatasystem.repo.RoleRepository;
import com.innowise.employeedatasystem.util.GeneralConstant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Map;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class RoleMapper {

    @Autowired
    private RoleRepository roleRepository;

    public String mapToDto(Role role) {
        if (role == null)
            return null;
        return role.getRole().name();
    }

    public Role mapToEntity(String stringRole) {
        if (stringRole == null)
            return null;
        try {
            RoleEnum roleEnum = RoleEnum.valueOf(stringRole);
            return roleRepository.findByRole(roleEnum).orElseThrow(() -> new RoleIsNotFoundException(GeneralConstant.Message.ROLE_IS_NOT_FOUND_EXCEPTION_MESSAGE,
                    Instant.now(), Map.of(GeneralConstant.Field.ROLE_FIELD, stringRole)));
        } catch (IllegalArgumentException exception) {
            throw new RoleIsNotFoundException(GeneralConstant.Message.ROLE_IS_NOT_FOUND_EXCEPTION_MESSAGE,
                    Instant.now(), Map.of(GeneralConstant.Field.ROLE_FIELD, stringRole));
        }
    }
}
