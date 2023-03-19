package com.innowise.employeedatasystem.mapper;

import com.innowise.employeedatasystem.dto.RegistrationUserDto;
import com.innowise.employeedatasystem.dto.UserDto;
import com.innowise.employeedatasystem.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final RoleSetMapper roleSetMapper;

    public User toUserEntity(RegistrationUserDto registrationUserDto) {
        return User.builder()
                .username(registrationUserDto.getUsername())
                .password(registrationUserDto.getPassword())
                .roles(roleSetMapper.toRoleEntitySet(registrationUserDto.getRoles()))
                .mail(registrationUserDto.getMail())
                .enabled(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .build();
    }

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .mail(user.getMail())
                .roles(roleSetMapper.toRoleDtoSet(user.getRoles()))
                .enabled(user.isEnabled())
                .accountNonExpired(user.isAccountNonExpired())
                .accountNonLocked(user.isAccountNonLocked())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .build();
    }

}
