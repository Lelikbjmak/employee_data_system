package com.innowise.employeedatasystem.mapper;

import com.innowise.employeedatasystem.dto.RegistrationUserDto;
import com.innowise.employeedatasystem.dto.UserDto;
import com.innowise.employeedatasystem.entity.Role;
import com.innowise.employeedatasystem.entity.RoleEnum;
import com.innowise.employeedatasystem.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    @Order(1)
    @DisplayName(value = "Context loads")
    void contextLoads() {
        Assertions.assertNotNull(userMapper);
    }

    @Test
    @Order(2)
    @DisplayName("Map UserDto to User.")
    void mustReturnUserEntity() {

        final String username = "username";
        final Set<String> roleSet = Set.of(RoleEnum.ROLE_USER.name());

        User user = userMapper.mapToEntity(UserDto.builder()
                .username(username)
                .roles(roleSet)
                .build());

        Assertions.assertEquals(username, user.getUsername());
        Assertions.assertEquals(roleSet, user.getRoles().stream()
                .map(role -> role.getRole().name())
                .collect(Collectors.toSet()));
    }

    @Test
    @Order(3)
    @DisplayName("Map User to UserDto.")
    void mustReturnUserDto() {

        final String username = "username";
        final Set<Role> roleSet = Set.of(Role.builder()
                .role(RoleEnum.ROLE_USER)
                .build());

        User user = User.builder()
                .username(username)
                .roles(roleSet)
                .build();

        UserDto userDto = userMapper.mapToDto(user);

        Assertions.assertEquals(username, userDto.username());
        Assertions.assertEquals(roleSet.stream()
                .map(role -> role.getRole().name())
                .collect(Collectors.toSet()), userDto.roles());
    }

    @Test
    @Order(4)
    @DisplayName(value = "Map Null to Null")
    void mustReturnNull() {
        RegistrationUserDto registrationUserDto = null;
        UserDto userDto = null;
        Assertions.assertNull(userMapper.mapToEntity(userDto));
        Assertions.assertNull(userMapper.mapToEntity(registrationUserDto));
        Assertions.assertNull(userMapper.mapToDto(null));
    }
}