package com.innowise.employeedatasystem.repository;

import com.innowise.employeedatasystem.entity.Employee;
import com.innowise.employeedatasystem.entity.Role;
import com.innowise.employeedatasystem.entity.RoleEnum;
import com.innowise.employeedatasystem.entity.User;
import com.innowise.employeedatasystem.repo.EmployeeRepository;
import com.innowise.employeedatasystem.repo.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Date;
import java.util.Set;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository, EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
    }

    @Test
    @DisplayName(value = "Context loads")
    void contextLoads() {
        Assertions.assertNotNull(userRepository);
        Assertions.assertNotNull(employeeRepository);
    }

    @Test
    @DisplayName(value = "`SAVE` User")
    @Sql(value = "/sql/before-registration.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void successSaveEmployee(
            @Value(value = "${user.username}") String username,
            @Value(value = "${user.password}") String password,
            @Value(value = "${user.mail}") String mail,
            @Value(value = "${employee.first-name}") String firstName,
            @Value(value = "${employee.last-name}") String lastName,
            @Value(value = "${employee.middle-name}") String middleName) {

        Employee employee = Employee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .middleName(middleName)
                .hireDate(new Date())
                .build();

        User user = User.builder()
                .username(username)
                .password(password)
                .mail(mail)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .accountNonExpired(true)
                .enabled(true)
                .roles(Set.of(Role.builder()
                        .role(RoleEnum.ROLE_ADMIN).build()))
                .build();

        user.setEmployee(employee);
        employee.setUser(user);

        User savedUser = userRepository.save(user);
        Assertions.assertNotNull(savedUser);

        Employee savedEmployee = employeeRepository.save(employee);
        Assertions.assertNotNull(savedEmployee);
    }

    @Test
    @DisplayName(value = "Success `FIND` User by username")
    @Sql(value = "/sql/create-employee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void successFindUserByUsername(@Value(value = "${user.username}") String username) {
        Assertions.assertTrue(userRepository.findByUsername(username).isPresent());
    }

    @Test
    @DisplayName(value = "Failed `FIND` User by username")
    @Sql(value = "/sql/create-employee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void failedFindUseByUsername(@Value(value = "${user.username}") String username) {
        Assertions.assertTrue(userRepository.findByUsername(username).isPresent());
    }

    @Test
    @DisplayName(value = "Failed `FIND` User by ID")
    @Sql(value = "/sql/create-employee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void failedFindUserById() {
        final long failedId = 100;
        Assertions.assertTrue(userRepository.findById(failedId).isEmpty());
    }

    @Test
    @DisplayName(value = "Success `FIND` User by ID")
    @Sql(value = "/sql/create-employee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void successFindUserById(@Value(value = "${employee.id}") long id) {
        Assertions.assertTrue(userRepository.findById(id).isPresent());
    }

    @Test
    @DisplayName(value = "`DELETE` User")
    @Sql(value = "/sql/create-employee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteSEmployee(@Value(value = "${employee.id}") long id) {

        Assertions.assertTrue(userRepository.findById(id).isPresent());
        userRepository.deleteById(id);
        Assertions.assertTrue(userRepository.findById(id).isEmpty());
    }
}
