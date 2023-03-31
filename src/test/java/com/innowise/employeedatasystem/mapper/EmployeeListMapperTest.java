package com.innowise.employeedatasystem.mapper;

import com.innowise.employeedatasystem.dto.DeletedEmployeeDto;
import com.innowise.employeedatasystem.dto.EmployeeDto;
import com.innowise.employeedatasystem.dto.UpdatedEmployeeDto;
import com.innowise.employeedatasystem.entity.Employee;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeListMapperTest {

    @Autowired
    private EmployeeListMapper employeeListMapper;

    @Test
    @Order(1)
    @DisplayName(value = "Context loads")
    void contextLoads() {
        Assertions.assertNotNull(employeeListMapper);
    }

    @Test
    @Order(2)
    @DisplayName(value = "Map List<Employee> to List<EmployeeDto>")
    void mustReturnEmployeeDtoList() {

        List<Employee> employeeList = List.of(
                Employee.builder()
                        .firstName("firstName")
                        .build()
        );

        List<EmployeeDto> expectedEmployeeDto = List.of(
                EmployeeDto.builder()
                        .firstName("firstName")
                        .build()
        );

        List<EmployeeDto> actualEmployeeDtoList = employeeListMapper.mapToDto(employeeList);
        Assertions.assertEquals(expectedEmployeeDto.get(0).getFirstName(),
                actualEmployeeDtoList.get(0).getFirstName());
    }

    @Test
    @Order(3)
    @DisplayName(value = "Map List<EmployeeDto> to List<DeletedEmployeeDto>")
    void mustReturnDeletedEmployeeList() {

        List<Employee> employeeList = List.of(
                Employee.builder()
                        .firstName("firstName")
                        .build()
        );

        List<DeletedEmployeeDto> expectedDeletedEmployeeDtoList = List.of(
                DeletedEmployeeDto.builder()
                        .firstName("firstName")
                        .isDeleted(true)
                        .build()
        );

        List<DeletedEmployeeDto> actualDeletedEmployeeDtoList = employeeListMapper.mapToDeletedDtoList(employeeList);
        Assertions.assertEquals(expectedDeletedEmployeeDtoList.get(0).getFirstName(),
                actualDeletedEmployeeDtoList.get(0).getFirstName());
        Assertions.assertTrue(actualDeletedEmployeeDtoList.get(0).isDeleted());
    }

    @Test
    @Order(4)
    @DisplayName(value = "Map List<EmployeeDto> to List<DeletedEmployeeDto>")
    void mustReturnUpdatedEmployeeList() {

        List<Employee> employeeList = List.of(
                Employee.builder()
                        .firstName("firstName")
                        .build()
        );

        List<UpdatedEmployeeDto> expectedUpdatedEmployeeDtoList = List.of(
                UpdatedEmployeeDto.builder()
                        .firstName("firstName")
                        .isUpdated(true)
                        .build()
        );

        List<UpdatedEmployeeDto> actualUpdatedEmployeeDtoList = employeeListMapper.mapToUpdatedDtoList(employeeList);
        Assertions.assertEquals(expectedUpdatedEmployeeDtoList.get(0).getFirstName(),
                actualUpdatedEmployeeDtoList.get(0).getFirstName());
        Assertions.assertTrue(actualUpdatedEmployeeDtoList.get(0).isUpdated());
    }

    @Test
    @Order(5)
    @DisplayName(value = "Map List<EmployeeDto> to List<DeletedEmployeeDto>")
    void mustReturnNull() {
        Assertions.assertNull(employeeListMapper.mapToDto(null));
        Assertions.assertNull(employeeListMapper.mapToUpdatedDtoList(null));
        Assertions.assertNull(employeeListMapper.mapToDeletedDtoList(null));
    }
}