package com.innowise.employeedatasystem.repository;

import com.innowise.employeedatasystem.entity.Employee;
import com.innowise.employeedatasystem.repo.EmployeeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Date;

@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeRepositoryTest {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeRepositoryTest(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Test
    @Order(1)
    @DisplayName(value = "Context loads")
    void contextLoadsTest() {
        Assertions.assertNotNull(employeeRepository);
    }

    @Test
    @Order(2)
    @DisplayName(value = "`SAVE` Employee")
    void mustSaveEmployeeTest(
            @Value(value = "${employee.first-name}") String firstName,
            @Value(value = "${employee.last-name}") String lastName,
            @Value(value = "${employee.middle-name}") String middleName) {

        Employee employee = Employee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .middleName(middleName)
                .hireDate(new Date())
                .build();

        Employee savedEmployee = employeeRepository.save(employee);
        Assertions.assertNotNull(savedEmployee);
    }

    @Test
    @Order(3)
    @DisplayName(value = "`FIND` Employee by ID")
    @Sql(value = "/sql/create-employee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void mustFindEmployeeByIdTest(@Value(value = "${employee.id}") long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(RuntimeException::new);
        Assertions.assertNotNull(employee);
    }

    @Test
    @Order(4)
    @DisplayName(value = "NOT `FIND` Employee by ID")
    @Sql(value = "/sql/create-employee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void mustNotFindEmployeeByIdTest() {
        final long failedId = 100;
        Assertions.assertTrue(employeeRepository.findById(failedId).isEmpty());
    }

    @Test
    @Order(5)
    @DisplayName(value = "`DELETE` Employee")
    @Sql(value = "/sql/create-employee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void mustDeleteEmployeeTest(@Value(value = "${employee.id}") long id) {

        Assertions.assertTrue(employeeRepository.findById(id).isPresent());
        employeeRepository.deleteById(id);
        Assertions.assertTrue(employeeRepository.findById(id).isEmpty());
    }

    @Test
    @Order(6)
    @DisplayName(value = "`EDIT` Employee")
    @Sql(value = "/sql/create-employee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void mustEditEmployeeTest(@Value(value = "${employee.id}") long id) {

        final String newLastName = "UPDATE";

        Employee employee = employeeRepository.findById(id).orElse(null);

        assert employee != null;
        employee.setLastName(newLastName);

        employeeRepository.save(employee);

        Employee updatedEmployee = employeeRepository.findById(id).orElse(null);
        Assertions.assertNotNull(updatedEmployee);
        Assertions.assertEquals(newLastName, updatedEmployee.getLastName());
    }

    @Test
    @Order(7)
    @DisplayName(value = "`FIND` Employee By User username")
    @Sql(value = "/sql/create-employee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void mustFindEmployeeByUsernameTest(@Value(value = "${user.username}") String username) {
        Assertions.assertTrue(employeeRepository.findByUser_username(username).isPresent());
    }

    @Test
    @Order(8)
    @DisplayName(value = "Failed `FIND` Employee By User username")
    @Sql(value = "/sql/create-employee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void mustNotFindEmployeeByUsernameTest() {
        final String wrongUsername = "wrongUsername";
        Assertions.assertTrue(employeeRepository.findByUser_username(wrongUsername).isEmpty());
    }

    @Test
    @Order(9)
    @DisplayName(value = "Success `FIND` Employee By several attributes")
    @Sql(value = "/sql/create-employee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void mustNotFindEmployeeBySeveralAttributesTest(
            @Value(value = "${existing-employee.first-name}") String firstName,
            @Value(value = "${existing-employee.last-name}") String lastName,
            @Value(value = "${existing-employee.middle-name}") String middleName) {
        Assertions.assertNotNull(employeeRepository.findByFirstNameAndLastNameAndMiddleNameAndHireDate(firstName,
                lastName, middleName, new Date()));
    }

    @Test
    @Order(10)
    @DisplayName(value = "Success `FIND` Employees By User.username in (...)")
    @Sql(value = "/sql/create-employee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void mustFindEmployeeListByUsernameList(@Value(value = "${user.username}") String username) {

    }
}
