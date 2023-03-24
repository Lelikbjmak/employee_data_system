package com.innowise.employeedatasystem.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.employeedatasystem.EmployeeDataSystemApplication;
import com.innowise.employeedatasystem.dto.*;
import com.innowise.employeedatasystem.exception.EmployeeIsNotFoundException;
import com.innowise.employeedatasystem.serviceimpl.EmployeeServiceImpl;
import com.innowise.employeedatasystem.util.Constant;
import com.innowise.employeedatasystem.util.GeneralConstant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {EmployeeDataSystemApplication.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = "/sql/create-employee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class EmployeeControllerTest {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    private final EmployeeServiceImpl employeeService;

    @Autowired
    public EmployeeControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, EmployeeServiceImpl employeeService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.employeeService = employeeService;
    }

    @Test
    @DisplayName(value = "Context loads")
    void contextLoads() {
        Assertions.assertNotNull(objectMapper);
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    @DisplayName(value = "`Get` Employee by Id.")
    @WithMockUser(authorities = {"ROLE_USER"})
    void successGetEmployeeById(@Value(value = "${employee.id}") Long id) throws Exception {

        String responseString = mockMvc.perform(get(Constant.ApiRoutes.GET_X + "/" + id)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        EmployeeDto employeeDto = objectMapper.readValue(responseString, EmployeeDto.class);
        Assertions.assertNotNull(employeeDto, "Response can't be deserialized to EmployeeDto.class.");
        Assertions.assertEquals(id, employeeDto.getId());
    }

    @Test
    @DisplayName(value = "`Get` Employee by Id (EMPLOYEE NOT FOUND).")
    @WithMockUser(authorities = {"ROLE_USER"})
    void failedGetEmployeeById() throws Exception {

        final long invalidId = 100L;

        String responseString = mockMvc.perform(get(Constant.ApiRoutes.GET_X + "/" + invalidId)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        ExceptionResponseDto failedResponseDto = objectMapper.readValue(responseString, ExceptionResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to ExceptionResponseDto.class.");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), failedResponseDto.getCode());
        Assertions.assertEquals(GeneralConstant.Message.EMPLOYEE_IS_NOT_FOUND_BY_ID_EXCEPTION_MESSAGE, failedResponseDto.getMessage());
    }

    @Test
    @DisplayName(value = "`Get` all Employees.")
    @WithMockUser(authorities = {"ROLE_USER"})
    void successGetAllEmployees() throws Exception {

        int size = employeeService.getAllEmployees().size();

        String responseString = mockMvc.perform(get(Constant.ApiRoutes.GET_ALL_X)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<EmployeeDto> employeeDtoList = objectMapper.readValue(responseString, List.class);

        Assertions.assertNotNull(employeeDtoList, "Response can't be deserialized to List<EmployeeDto>.class.");
        Assertions.assertEquals(size, employeeDtoList.size());
    }

    @Test
    @DisplayName(value = "`Get` Employee by username.")
    @WithMockUser(authorities = {"ROLE_USER"})
    void successGetEmployeeByUserUsername(@Value(value = "${user.username}") String username) throws Exception {

        String responseString = mockMvc.perform(get(Constant.ApiRoutes.GET_X)
                        .param(GeneralConstant.Field.USERNAME_FIELD, username)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        EmployeeDto employeeDto = objectMapper.readValue(responseString, EmployeeDto.class);
        Assertions.assertNotNull(employeeDto, "Response can't be deserialized to EmployeeDto.class.");
        Assertions.assertEquals(username, employeeDto.getUserDto().getUsername());
    }

    @Test
    @DisplayName(value = "`Get` Employee by username (EMPLOYEE NOT FOUND).")
    @WithMockUser(authorities = {"ROLE_USER"})
    void failedGetEmployeeByUserUsernameEmployeeNotFound() throws Exception {

        final String failedUsername = "wrongUsername";

        String responseString = mockMvc.perform(get(Constant.ApiRoutes.GET_X)
                        .param(GeneralConstant.Field.USERNAME_FIELD, failedUsername)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        ExceptionResponseDto failedResponseDto = objectMapper.readValue(responseString, ExceptionResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to ExceptionResponseDto.class.");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), failedResponseDto.getCode());
        Assertions.assertEquals(GeneralConstant.Message.EMPLOYEE_IS_NOT_FOUND_BY_ID_EXCEPTION_MESSAGE, failedResponseDto.getMessage());
    }

    @Test
    @DisplayName(value = "`Delete` Employee.")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void successDeleteEmployee(@Value(value = "${employee.id}") Long id) throws Exception {

        Assertions.assertNotNull(employeeService.getEmployeeById(id));

        String responseString = mockMvc.perform(delete(Constant.ApiRoutes.DELETE_X + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn().getResponse().getContentAsString();

        DeletedEmployeeDto deletedEmployeeDto = objectMapper.readValue(responseString, DeletedEmployeeDto.class);
        Assertions.assertNotNull(deletedEmployeeDto, "Response can't be deserialized to DeletedEmployeeDto.class.");

        Assertions.assertThrows(EmployeeIsNotFoundException.class, () ->
                employeeService.getEmployeeById(id));
    }

    @Test
    @DisplayName(value = "`Delete` Employee list.")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void successDeleteEmployeeList() throws Exception {
        List<Long> employeeIdToDelete = List.of(1L, 2L, 99L);

        Assertions.assertFalse(employeeService.getEmployeeListByIdList(employeeIdToDelete).isEmpty());

        String responseString = mockMvc.perform(delete(Constant.ApiRoutes.DELETE_ALL_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employeeIdToDelete)))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn().getResponse().getContentAsString();

        List<DeletedEmployeeDto> deletedEmployeeDto = objectMapper.readValue(responseString, List.class);
        Assertions.assertNotNull(deletedEmployeeDto, "Response can't be deserialized to DeletedEmployeeDto.class.");

        Assertions.assertTrue(employeeService.getEmployeeListByIdList(employeeIdToDelete).isEmpty());
    }

    @Test
    @DisplayName(value = "`Edit` Employee.")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void successEditEmployee() throws Exception {

        final String newLastName = "Updated";

        List<EmployeeDto> employeeDtoListToEdit = List.of(
                EmployeeDto.builder()
                        .id(1L)
                        .lastName(newLastName)
                        .build(),
                EmployeeDto.builder()
                        .id(2L)
                        .lastName(newLastName)
                        .build()
        );

        Assertions.assertNotEquals(newLastName, employeeService.getEmployeeById(1L).getLastName());
        Assertions.assertNotEquals(newLastName, employeeService.getEmployeeById(2L).getLastName());

        String responseString = mockMvc.perform(put(Constant.ApiRoutes.EDIT_ALL_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employeeDtoListToEdit))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn().getResponse().getContentAsString();

        List<UpdatedEmployeeDto> updatedEmployeeDto = objectMapper.readValue(responseString, List.class);
        Assertions.assertNotNull(updatedEmployeeDto, "Response can't be deserialized to DeletedEmployeeDto.class.");

        Assertions.assertEquals(newLastName, employeeService.getEmployeeById(1L).getLastName());
        Assertions.assertEquals(newLastName, employeeService.getEmployeeById(2L).getLastName());
    }

    @Test
    @DisplayName(value = "`Edit` Employee List.")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void successEditEmployeeList(@Value(value = "${employee.id}") Long id) throws Exception {

        final String newLastName = "Updated";

        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(id)
                .lastName(newLastName)
                .build();

        Assertions.assertNotEquals(newLastName, employeeService.getEmployeeById(id).getLastName());

        String responseString = mockMvc.perform(put(Constant.ApiRoutes.EDIT_X + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employeeDto))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn().getResponse().getContentAsString();

        UpdatedEmployeeDto updatedEmployeeDto = objectMapper.readValue(responseString, UpdatedEmployeeDto.class);
        Assertions.assertNotNull(updatedEmployeeDto, "Response can't be deserialized to DeletedEmployeeDto.class.");

        Assertions.assertEquals(newLastName, employeeService.getEmployeeById(id).getLastName());
    }

    @Test
    @DisplayName(value = "`Add` Employee.")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/before-registration.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void successAddEmployee(
            @Value(value = "${user.username}") String username, @Value(value = "${user.mail}") String mail,
            @Value(value = "${user.password}") String password, @Value(value = "${employee.first-name}") String firstName,
            @Value(value = "${employee.last-name}") String lastName, @Value(value = "${employee.middle-name}") String middleName,
            @Value(value = "${user.role-admin}") String roleAdmin) throws Exception {

        RegistrationDto registrationDto = RegistrationDto.builder()
                .userDto(RegistrationUserDto.builder()
                        .username(username)
                        .password(password)
                        .mail(mail)
                        .roles(Set.of(roleAdmin))
                        .build())
                .employeeDto(RegistrationEmployeeDto.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .middleName(middleName)
                        .hireDate(new Date())
                        .build())
                .build();

        mockMvc.perform(post(Constant.ApiRoutes.ADD_ALL_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(List.of(registrationDto)))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(1, employeeService.getAllEmployees().size());
    }


    @Test
    @DisplayName(value = "`Add` Employee (ROLE IS NOT FOUND).")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void failedAddEmployeeInvalidRole() throws Exception {

        RegistrationDto registrationDto = RegistrationDto.builder()
                .userDto(RegistrationUserDto.builder()
                        .roles(Set.of("INVALID"))
                        .build())
                .employeeDto(RegistrationEmployeeDto.builder()
                        .hireDate(new Date())
                        .build())
                .build();

        String responseString = mockMvc.perform(post(Constant.ApiRoutes.ADD_ALL_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(List.of(registrationDto)))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        ExceptionResponseDto failedResponseDto = objectMapper.readValue(responseString, ExceptionResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to ExceptionResponseDto.class.");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), failedResponseDto.getCode());
        Assertions.assertEquals(GeneralConstant.Message.ROLE_IS_NOT_FOUND_EXCEPTION_MESSAGE, failedResponseDto.getMessage());
    }
}
