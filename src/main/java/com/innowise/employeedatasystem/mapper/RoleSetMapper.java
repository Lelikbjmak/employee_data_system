package com.innowise.employeedatasystem.mapper;

import com.innowise.employeedatasystem.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {RoleMapper.class})
public interface RoleSetMapper {

    Set<Role> toRoleEntitySet(Set<String> roles);

    Set<String> toRoleDtoSet(Set<Role> roles);

}
