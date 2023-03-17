package com.innowise.employeedatasystem.mapper;

import com.innowise.employeedatasystem.dto.UserDto;
import com.innowise.employeedatasystem.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final RoleSetMapper roleSetMapper;

    public User toUserEntity(UserDto registrationUserDto) {
        return User.builder()
                .username(registrationUserDto.getUsername())
                .password(registrationUserDto.getPassword())
                .roles(roleSetMapper.toRoleEntitySet(registrationUserDto.getRoles()))
                .mail(registrationUserDto.getMail())
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .enabled(true)
                .build();
    }

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .mail(user.getMail())
                .roles(roleSetMapper.toRoleDtoSet(user.getRoles()))
                .build();
    }

}
