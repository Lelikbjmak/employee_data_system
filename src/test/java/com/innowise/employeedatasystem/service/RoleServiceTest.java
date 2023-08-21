package com.innowise.employeedatasystem.service;

import com.innowise.employeedatasystem.entity.Role;
import com.innowise.employeedatasystem.entity.RoleEnum;
import com.innowise.employeedatasystem.exception.RoleIsNotFoundException;
import com.innowise.employeedatasystem.repo.RoleRepository;
import com.innowise.employeedatasystem.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    @Order(2)
    @DisplayName(value = "Find Role by RoleName(RoelEnum)")
    void mustReturnRoleByRoleEnum() {

        final RoleEnum roleEnum = RoleEnum.ROLE_ADMIN;
        final Role role = Role.builder()
                .role(roleEnum)
                .build();

        Mockito.when(roleRepository.findByRole(roleEnum))
                .thenReturn(Optional.of(role));

        Role foundRole = roleService.findByRole(roleEnum);

        Mockito.verify(roleRepository, Mockito.times(1)).findByRole(roleEnum);

        Assertions.assertNotNull(foundRole);
        Assertions.assertEquals(role, foundRole);
    }

    @Test
    @Order(2)
    @DisplayName(value = "Find Role by RoleName(RoelEnum). Throw RoleIsNotFoundException.")
    void mustThrowExceptionInFindByRoleEnum() {

        final RoleEnum roleEnum = RoleEnum.ROLE_ADMIN;

        Mockito.when(roleRepository.findByRole(roleEnum))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(RoleIsNotFoundException.class, () ->
                roleService.findByRole(roleEnum));

        Mockito.verify(roleRepository, Mockito.times(1)).findByRole(roleEnum);
    }
}
