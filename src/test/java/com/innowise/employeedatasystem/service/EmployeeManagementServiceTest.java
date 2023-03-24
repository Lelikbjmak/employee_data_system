package com.innowise.employeedatasystem.service;

import com.innowise.employeedatasystem.dto.*;
import com.innowise.employeedatasystem.entity.Employee;
import com.innowise.employeedatasystem.entity.User;
import com.innowise.employeedatasystem.mapper.EmployeeMapper;
import com.innowise.employeedatasystem.mapper.UserMapper;
import com.innowise.employeedatasystem.serviceimpl.EmployeeManagementServiceImpl;
import com.innowise.employeedatasystem.serviceimpl.EmployeeServiceImpl;
import com.innowise.employeedatasystem.serviceimpl.UserServiceImpl;
import com.innowise.employeedatasystem.util.GeneralConstant;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeManagementServiceTest {

    @Mock
    private EmployeeServiceImpl employeeService;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeManagementServiceImpl employeeManagementService;

    @Test
    @Order(1)
    @DisplayName(value = "Context loads test")
    void contextLoadTest() {
        Assertions.assertNotNull(employeeManagementService);
        Assertions.assertNotNull(employeeService);
        Assertions.assertNotNull(userService);
        Assertions.assertNotNull(employeeMapper);
        Assertions.assertNotNull(userMapper);
    }

    @Test
    @Order(2)
    @DisplayName(value = "`Register` Employee List")
    void mustReturnRegistrationEmployeeDtoListTest() {

        User mockUser = User.builder().build();
        Employee mockEmployee = Employee.builder().build();
        EmployeeDto mockEmployeeDto = EmployeeDto.builder().build();

        RegistrationDto mockRegistrationDto = RegistrationDto.builder()
                .employeeDto(RegistrationEmployeeDto.builder().build())
                .userDto(RegistrationUserDto.builder().build())
                .build();

        List<RegistrationDto> registrationDtoList = List.of(mockRegistrationDto);

        when(userMapper.toUserEntity(any(RegistrationUserDto.class))).thenReturn(mockUser);
        when(employeeMapper.toEmployeeEntity(any(RegistrationEmployeeDto.class))).thenReturn(mockEmployee);
        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(mockEmployee);
        when(employeeMapper.toEmployeeDto(any(Employee.class))).thenReturn(mockEmployeeDto);

        RegistrationResponseDto actualResponse = employeeManagementService.registerEmployeeList(registrationDtoList);

        assertEquals(HttpStatus.CREATED.value(), actualResponse.getCode());
        assertEquals(HttpStatus.CREATED.name(), actualResponse.getStatus());
        assertEquals(GeneralConstant.Message.EMPLOYEE_SUCCESSFULLY_ADDED_MESSAGE, actualResponse.getMessage());
        assertEquals(registrationDtoList.size(), actualResponse.getContent().size());

        verify(userService, times(1)).registerUser(mockUser);
        verify(employeeService, times(1)).saveEmployee(mockEmployee);
    }

    @Test
    @Order(3)
    @DisplayName(value = "`Register` Employee")
    void mustReturnRegistrationEmployeeDtoTest() {

        User mockUser = User.builder().build();
        Employee mockEmployee = Employee.builder().build();
        EmployeeDto mockEmployeeDto = EmployeeDto.builder().build();

        RegistrationDto mockRegistrationDto = RegistrationDto.builder()
                .employeeDto(RegistrationEmployeeDto.builder().build())
                .userDto(RegistrationUserDto.builder().build())
                .build();

        when(userMapper.toUserEntity(any(RegistrationUserDto.class))).thenReturn(mockUser);
        when(employeeMapper.toEmployeeEntity(any(RegistrationEmployeeDto.class))).thenReturn(mockEmployee);
        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(mockEmployee);
        when(employeeMapper.toEmployeeDto(any(Employee.class))).thenReturn(mockEmployeeDto);

        RegistrationResponseDto actualResponse = employeeManagementService.registerEmployee(mockRegistrationDto);

        assertEquals(HttpStatus.CREATED.value(), actualResponse.getCode());
        assertEquals(HttpStatus.CREATED.name(), actualResponse.getStatus());
        assertEquals(GeneralConstant.Message.EMPLOYEE_SUCCESSFULLY_ADDED_MESSAGE, actualResponse.getMessage());
        assertEquals(List.of(mockRegistrationDto).size(), actualResponse.getContent().size());

        verify(userService, times(1)).registerUser(mockUser);
        verify(employeeService, times(1)).saveEmployee(mockEmployee);
    }

    @Test
    @Order(4)
    @DisplayName(value = "`Get` all Employees as Dto")
    void mustReturnEmployeeDtoListTest() {

        List<Employee> employeeList = List.of(
                Employee.builder()
                        .id(1L)
                        .build(),
                Employee.builder()
                        .id(2L)
                        .build()
        );

        List<EmployeeDto> expectedEmployeeDtoList = List.of(
                EmployeeDto.builder()
                        .id(1L)
                        .build(),
                EmployeeDto.builder()
                        .id(2L)
                        .build()
        );

        when(employeeService.getAllEmployees()).thenReturn(employeeList);
        when(employeeMapper.toEmployeeDto(any(Employee.class)))
                .thenAnswer(invocationOnMock -> {
                    Employee employee = invocationOnMock.getArgument(0);
                    return EmployeeDto.builder()
                            .id(employee.getId())
                            .build();
                });

        List<EmployeeDto> actualEmployeeDtoList = employeeManagementService.getAllEmployees();

        verify(employeeService, times(1)).getAllEmployees();

        for (Employee employee :
                employeeList) {
            verify(employeeMapper).toEmployeeDto(employee);
        }

        actualEmployeeDtoList.forEach(p -> System.err.println(p.getId()));
        for (int i = 0; i < expectedEmployeeDtoList.size(); i++) {
            assertEquals(expectedEmployeeDtoList.get(i).getId(),
                    actualEmployeeDtoList.get(i).getId());
        }
    }

    @Test
    @Order(5)
    @DisplayName(value = "`Edit` Employee")
    void mustUpdateSingleEmployeeTest() {

        final String oldFirstName = "OLD";
        final String newFirstName = "NEW";
        final Long employeeId = 1L;

        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName(newFirstName)
                .build();

        Employee existingEmployee = Employee.builder()
                .id(employeeId)
                .firstName(oldFirstName)
                .build();

        UpdatedEmployeeDto updatedEmployeeDto = UpdatedEmployeeDto.builder()
                .id(employeeId)
                .firstName(newFirstName)
                .build();

        when(employeeService.getEmployeeById(employeeId)).thenReturn(existingEmployee);
        Employee expectedUpdatedEmployee = Employee.builder()
                .id(employeeId)
                .firstName("NEW")
                .build();
        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(expectedUpdatedEmployee);
        when(employeeMapper.toUpdatedEmployeeDto(expectedUpdatedEmployee)).thenReturn(updatedEmployeeDto);

        UpdatedEmployeeDto editedEmployee = employeeManagementService.editEmployee(employeeId, employeeDto);
        Assertions.assertEquals(expectedUpdatedEmployee.getFirstName(), editedEmployee.getFirstName());
    }
}
