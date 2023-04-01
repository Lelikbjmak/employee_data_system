package com.innowise.employeedatasystem.mapper;

import com.innowise.employeedatasystem.dto.DeletedEmployeeDto;
import com.innowise.employeedatasystem.dto.EmployeeDto;
import com.innowise.employeedatasystem.dto.UpdatedEmployeeDto;
import com.innowise.employeedatasystem.dto.UserDto;
import com.innowise.employeedatasystem.entity.Employee;
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
class EmployeeMapperTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    @Order(1)
    @DisplayName(value = "Context loads")
    void contextLoads() {
        Assertions.assertNotNull(employeeMapper);
    }

    @Test
    @Order(2)
    @DisplayName(value = "Map EmployeeDto to Employee.")
    void mustReturnEmployee() {

        final String firstName = "firstName";
        final String username = "username";
        final Set<String> roleSet = Set.of(RoleEnum.ROLE_USER.name());

        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName(firstName)
                .userDto(UserDto.builder()
                        .username(username)
                        .roles(roleSet)
                        .build())
                .build();

        Employee employee = employeeMapper.mapToEntity(employeeDto);

        Assertions.assertNotNull(employee);
        Assertions.assertEquals(firstName, employee.getFirstName());
        Assertions.assertEquals(username, employee.getUser().getUsername());
        Assertions.assertEquals(roleSet, employee.getUser().getRoles().stream()
                .map(role -> role.getRole().name())
                .collect(Collectors.toSet()));
    }

    @Test
    @Order(3)
    @DisplayName(value = "Map Employee to EmployeeDto.")
    void mustReturnEmployeeDto() {

        final String firstName = "firstName";
        final String username = "username";
        final Set<Role> roleSet = Set.of(Role.builder()
                .role(RoleEnum.ROLE_USER)
                .build());

        Employee employee = Employee.builder()
                .firstName(firstName)
                .user(User.builder()
                        .username(username)
                        .roles(roleSet)
                        .build())
                .build();

        EmployeeDto employeeDto = employeeMapper.mapToDto(employee);

        Assertions.assertNotNull(employeeDto);
        Assertions.assertEquals(firstName, employeeDto.firstName());
        Assertions.assertEquals(username, employeeDto.userDto().username());
        Assertions.assertEquals(roleSet.stream()
                .map(role -> role.getRole().name())
                .collect(Collectors.toSet()), employeeDto.userDto().roles());
    }

    @Test
    @Order(3)
    @DisplayName(value = "Map Employee to DeletedEmployeeDto.")
    void mustReturnDeletedEmployeeDto() {

        final String firstName = "firstName";

        Employee employee = Employee.builder()
                .firstName(firstName)
                .build();

        DeletedEmployeeDto employeeDto = employeeMapper.mapToDeletedEmployeeDto(employee);

        Assertions.assertNotNull(employeeDto);
        Assertions.assertEquals(firstName, employeeDto.firstName());
        Assertions.assertTrue(employeeDto.isDeleted());
    }

    @Test
    @Order(3)
    @DisplayName(value = "Map Employee to UpdatedEmployeeDto.")
    void mustReturnUpdatedEmployeeDto() {

        final String firstName = "firstName";

        Employee employee = Employee.builder()
                .firstName(firstName)
                .build();

        UpdatedEmployeeDto employeeDto = employeeMapper.mapToUpdatedEmployeeDto(employee);

        Assertions.assertNotNull(employeeDto);
        Assertions.assertEquals(firstName, employeeDto.firstName());
        Assertions.assertTrue(employeeDto.isUpdated());
    }

    @Test
    @Order(4)
    @DisplayName(value = "Map Employee to UpdatedEmployeeDto.")
    void mustReturnNullEmployee() {

        EmployeeDto employeeDto = null;

        Employee employee = employeeMapper.mapToEntity(employeeDto);

        Assertions.assertNull(employee);
    }
}
