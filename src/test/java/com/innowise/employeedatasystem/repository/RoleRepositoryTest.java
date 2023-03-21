package com.innowise.employeedatasystem.repository;

import com.innowise.employeedatasystem.entity.Role;
import com.innowise.employeedatasystem.entity.RoleEnum;
import com.innowise.employeedatasystem.repo.RoleRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleRepositoryTest {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleRepositoryTest(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Test
    @Order(1)
    @DisplayName(value = "Context loads")
    void contextLoads() {
        Assertions.assertNotNull(roleRepository);
    }

    @Test
    @Order(2)
    @DisplayName(value = "`SAVE` Role")
    void successSaveRole() {

        Role role = Role.builder()
                .id(1L)
                .role(RoleEnum.ROLE_ADMIN)
                .build();

        Role savedRole = roleRepository.save(role);
        Assertions.assertNotNull(savedRole);
    }

    @Test
    @Order(3)
    @DisplayName(value = "Success `FIND` Role by ID")
    @Sql(value = "/sql/create-employee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void successFindRole(@Value(value = "${employee.id}") long id) {
        Role role = roleRepository.findById(id).orElse(null);
        Assertions.assertNotNull(role);
    }

    @Test
    @Order(4)
    @DisplayName(value = "Success `FIND` Role by RoleEnum")
    @Sql(value = "/sql/create-employee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void successFindRole() {
        Role role = roleRepository.findByRole(RoleEnum.ROLE_ADMIN).orElse(null);
        Assertions.assertNotNull(role);
    }

    @Test
    @Order(5)
    @DisplayName(value = "Failed `FIND` Role by RoleEnum")
    @Sql(value = "/sql/create-employee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void failedFindRole() {
        Assertions.assertTrue(roleRepository.findByRole(RoleEnum.ROLE_STAFF).isEmpty());
    }

    @Test
    @Order(6)
    @DisplayName(value = "Success `FIND` Role by RoleEnum")
    void successDeleteRole() {

        Role role = roleRepository.save(Role.builder()
                .role(RoleEnum.ROLE_ADMIN)
                .build());
        Assertions.assertNotNull(role);
        roleRepository.delete(role);
        Assertions.assertTrue(roleRepository.findByRole(RoleEnum.ROLE_ADMIN).isEmpty());
    }
}


