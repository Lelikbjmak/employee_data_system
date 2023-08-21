package com.innowise.employeedatasystem.service;

import com.innowise.employeedatasystem.dto.*;
import com.innowise.employeedatasystem.entity.Employee;
import com.innowise.employeedatasystem.entity.User;
import com.innowise.employeedatasystem.mapper.EmployeeListMapper;
import com.innowise.employeedatasystem.mapper.EmployeeMapper;
import com.innowise.employeedatasystem.mapper.UserMapper;
import com.innowise.employeedatasystem.provider.RegistrationResponseProvider;
import com.innowise.employeedatasystem.service.impl.EmployeeManagementServiceImpl;
import com.innowise.employeedatasystem.service.impl.EmployeeServiceImpl;
import com.innowise.employeedatasystem.service.impl.UserServiceImpl;
import com.innowise.employeedatasystem.util.GeneralConstant;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//TODO: Fix tests according to EmployeeListMapper + ResponseProvider
class EmployeeManagementServiceTest {

    @Mock
    private EmployeeServiceImpl employeeService;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private EmployeeListMapper employeeListMapper;

    @Mock
    private RegistrationResponseProvider responseProvider;

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

        when(userMapper.mapToEntity(any(RegistrationUserDto.class))).thenReturn(mockUser);
        when(employeeMapper.mapToEntity(any(RegistrationEmployeeDto.class))).thenReturn(mockEmployee);
        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(mockEmployee);
        when(employeeMapper.mapToDto(any(Employee.class))).thenReturn(mockEmployeeDto);
        when(responseProvider.generateRegistrationResponse(anyList())).thenReturn(RegistrationResponseDto.builder()
                .timestamp(new Date())
                .status(HttpStatus.CREATED.name())
                .code(HttpStatus.CREATED.value())
                .message(GeneralConstant.Message.EMPLOYEE_SUCCESSFULLY_ADDED_MESSAGE)
                .additional(anyList())
                .build());

        RegistrationResponseDto actualResponse = employeeManagementService.registerEmployeeList(registrationDtoList);

        assertEquals(HttpStatus.CREATED.value(), actualResponse.code());
        assertEquals(HttpStatus.CREATED.name(), actualResponse.status());
        assertEquals(GeneralConstant.Message.EMPLOYEE_SUCCESSFULLY_ADDED_MESSAGE, actualResponse.message());

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

        when(userMapper.mapToEntity(any(RegistrationUserDto.class))).thenReturn(mockUser);
        when(employeeMapper.mapToEntity(any(RegistrationEmployeeDto.class))).thenReturn(mockEmployee);
        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(mockEmployee);
        when(employeeMapper.mapToDto(any(Employee.class))).thenReturn(mockEmployeeDto);
        when(responseProvider.generateRegistrationResponse(anyList())).thenReturn(RegistrationResponseDto.builder()
                .timestamp(new Date())
                .status(HttpStatus.CREATED.name())
                .code(HttpStatus.CREATED.value())
                .message(GeneralConstant.Message.EMPLOYEE_SUCCESSFULLY_ADDED_MESSAGE)
                .additional(anyList())
                .build());

        RegistrationResponseDto actualResponse = employeeManagementService.registerEmployee(mockRegistrationDto);

        assertEquals(HttpStatus.CREATED.value(), actualResponse.code());
        assertEquals(HttpStatus.CREATED.name(), actualResponse.status());
        assertEquals(GeneralConstant.Message.EMPLOYEE_SUCCESSFULLY_ADDED_MESSAGE, actualResponse.message());

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
        when(employeeListMapper.mapToDto(employeeList)).thenReturn(expectedEmployeeDtoList);

        List<EmployeeDto> actualEmployeeDtoList = employeeManagementService.getAllEmployees();

        verify(employeeService, times(1)).getAllEmployees();

        for (int i = 0; i < expectedEmployeeDtoList.size(); i++) {
            assertEquals(expectedEmployeeDtoList.get(i).id(),
                    actualEmployeeDtoList.get(i).id());
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
        when(employeeMapper.mapToUpdatedEmployeeDto(expectedUpdatedEmployee)).thenReturn(updatedEmployeeDto);

        UpdatedEmployeeDto editedEmployee = employeeManagementService.editEmployee(employeeId, employeeDto);
        Assertions.assertEquals(expectedUpdatedEmployee.getFirstName(), editedEmployee.firstName());
    }
}
