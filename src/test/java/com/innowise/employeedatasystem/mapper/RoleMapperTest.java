package com.innowise.employeedatasystem.mapper;

import com.innowise.employeedatasystem.entity.Role;
import com.innowise.employeedatasystem.entity.RoleEnum;
import com.innowise.employeedatasystem.exception.RoleIsNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleMapperTest {

    @Autowired
    private RoleMapper roleMapper;

    @Test
    @Order(1)
    @DisplayName(value = "Context loads")
    void contextLoads() {
        Assertions.assertNotNull(roleMapper);
    }

    @Test
    @Order(3)
    @DisplayName(value = "Map String to Role.")
    void mustMapStringToEntity() {

        Role role = roleMapper.mapToEntity("ROLE_USER");
        Assertions.assertNotNull(role);
        Assertions.assertEquals(2, role.getId());
        Assertions.assertEquals(RoleEnum.ROLE_USER, role.getRole());
    }

    @Test
    @Order(3)
    @DisplayName(value = "Map Role to String.")
    void mustMapRoleToString() {

        String role = roleMapper.mapToDto(Role.builder()
                .id(1L)
                .role(RoleEnum.ROLE_ADMIN)
                .build());

        Assertions.assertNotNull(role);
        Assertions.assertEquals(RoleEnum.ROLE_ADMIN.name(), role);
    }

    @Test
    @Order(4)
    @DisplayName(value = "Map String to Role (FAILED ROLE NOT EXISTS).")
    void mustThrowRoleNotFoundException() {
        Assertions.assertThrows(RoleIsNotFoundException.class,
                () -> roleMapper.mapToEntity("INVALID"));
    }

    @Test
    @Order(5)
    @DisplayName(value = "Map Null to Null")
    void mustReturnNull() {
        Assertions.assertNull(roleMapper.mapToEntity(null));
        Assertions.assertNull(roleMapper.mapToDto(null));
    }
}