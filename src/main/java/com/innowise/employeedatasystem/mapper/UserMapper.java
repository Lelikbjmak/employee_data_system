package com.innowise.employeedatasystem.mapper;

import com.innowise.employeedatasystem.dto.RegistrationUserDto;
import com.innowise.employeedatasystem.dto.UserDto;
import com.innowise.employeedatasystem.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = RoleSetMapper.class)
public interface UserMapper {

    User mapToEntity(UserDto userDto);

    User mapToEntity(RegistrationUserDto userDto);

    UserDto mapToDto(User user);
}
