package com.innowise.employeedatasystem.mapper;

import com.innowise.employeedatasystem.entity.Role;
import com.innowise.employeedatasystem.entity.RoleEnum;
import com.innowise.employeedatasystem.exception.RoleIsNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleSetMapTest {

    @Autowired
    private RoleSetMapper roleSetMap;

    @Test
    @Order(1)
    @DisplayName(value = "Context loads")
    void contextLoads() {
        Assertions.assertNotNull(roleSetMap);
    }

    @Test
    @Order(2)
    @DisplayName(value = "Map Set<String> to Set<Role>.")
    void mustMapStringToEntity() {

        Set<Role> roleSet = roleSetMap.toRoleEntitySet(Set.of("ROLE_USER"));
        Assertions.assertNotNull(roleSet);
        Assertions.assertEquals(1, roleSet.size());
    }

    @Test
    @Order(3)
    @DisplayName(value = "Map Set<Role> to Set<String>.")
    void mustMapRoleToString() {

        Set<String> roleSet = roleSetMap.toRoleDtoSet(Set.of(Role.builder()
                .id(1L)
                .role(RoleEnum.ROLE_ADMIN)
                .build()));

        Assertions.assertNotNull(roleSet);
    }

    @Test
    @Order(4)
    @DisplayName(value = "Map Set<String> to Set<Role> (FAILED ROLE NOT EXISTS).")
    void mustThrowRoleNotFoundException() {
        Assertions.assertThrows(RoleIsNotFoundException.class,
                () -> roleSetMap.toRoleEntitySet(Set.of("INVALID")));
    }

    @Test
    @Order(4)
    @DisplayName(value = "Map Null to Null")
    void mustReturnNull() {
        Assertions.assertNull(roleSetMap.toRoleEntitySet(null));
        Assertions.assertNull(roleSetMap.toRoleDtoSet(null));
    }
}