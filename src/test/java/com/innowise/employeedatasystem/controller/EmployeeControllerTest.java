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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @DisplayName(value = "Get Employee by Id.")
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
    @DisplayName(value = "Get Employee by Id (NOT AUTHENTICATED).")
    void failedGetEmployeeByIdNotAuthenticated(@Value(value = "${employee.id}") Long id) throws Exception {

        String responseString = mockMvc.perform(get(Constant.ApiRoutes.GET_X + "/" + id)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse().getContentAsString();

        AuthenticationFailedResponseDto failedResponseDto = objectMapper.readValue(responseString, AuthenticationFailedResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to AuthenticationFailedResponseDto.class.");
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), failedResponseDto.getCode());
        Assertions.assertEquals(Constant.ApiRoutes.GET_X + "/" + id, failedResponseDto.getPath());
        Assertions.assertEquals(Constant.Message.NOT_AUTHENTICATED_MESSAGE, failedResponseDto.getMessage());
    }

    @Test
    @DisplayName(value = "Get Employee by Id (EMPLOYEE NOT FOUND).")
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
    @DisplayName(value = "Get all Employees.")
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
    @DisplayName(value = "Get all Employees (NOT AUTHENTICATED).")
    void failedGetAllEmployeesNotAuthenticated() throws Exception {

        String responseString = mockMvc.perform(get(Constant.ApiRoutes.GET_ALL_X)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse().getContentAsString();

        AuthenticationFailedResponseDto failedResponseDto = objectMapper.readValue(responseString, AuthenticationFailedResponseDto.class);

        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to AuthenticationFailedResponseDto.class.");
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), failedResponseDto.getCode());
        Assertions.assertEquals(Constant.ApiRoutes.GET_ALL_X, failedResponseDto.getPath());
        Assertions.assertEquals(Constant.Message.NOT_AUTHENTICATED_MESSAGE, failedResponseDto.getMessage());
    }

    @Test
    @DisplayName(value = "Get Employee by username.")
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
    @DisplayName(value = "Get Employee by username (NOT AUTHENTICATED).")
    void failedGetEmployeeByUserUsernameNotAuthenticated(@Value(value = "${user.username}") String username) throws Exception {

        String responseString = mockMvc.perform(get(Constant.ApiRoutes.GET_X)
                        .param(GeneralConstant.Field.USERNAME_FIELD, username)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse().getContentAsString();

        AuthenticationFailedResponseDto failedResponseDto = objectMapper.readValue(responseString, AuthenticationFailedResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to AuthenticationFailedResponseDto.class.");
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), failedResponseDto.getCode());
        Assertions.assertEquals(Constant.ApiRoutes.GET_X, failedResponseDto.getPath());
        Assertions.assertEquals(Constant.Message.NOT_AUTHENTICATED_MESSAGE, failedResponseDto.getMessage());
    }

    @Test
    @DisplayName(value = "Get Employee by username (EMPLOYEE NOT FOUND).")
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
    @DisplayName(value = "Delete Employee.")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void successDeleteEmployee(@Value(value = "${employee.id}") Long id) throws Exception {

        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(id)
                .build();

        Assertions.assertNotNull(employeeService.getEmployeeById(id));

        String responseString = mockMvc.perform(post(Constant.ApiRoutes.DELETE_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(List.of(employeeDto)))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn().getResponse().getContentAsString();

        List<DeletedEmployeeDto> deletedEmployeeDto = objectMapper.readValue(responseString, List.class);
        Assertions.assertNotNull(deletedEmployeeDto, "Response can't be deserialized to DeletedEmployeeDto.class.");

        Assertions.assertThrows(EmployeeIsNotFoundException.class, () ->
                employeeService.getEmployeeById(id));
    }

    @Test
    @DisplayName(value = "Delete Employee (ACCESS DENIED).")
    @WithMockUser(authorities = {"ROLE_USER"})
    void failedDeleteEmployeeNotCompliantAuthorities(@Value(value = "${employee.id}") Long id) throws Exception {

        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(id)
                .build();

        String responseString = mockMvc.perform(post(Constant.ApiRoutes.DELETE_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(List.of(employeeDto)))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn().getResponse().getContentAsString();

        ExceptionResponseDto failedResponseDto = objectMapper.readValue(responseString, ExceptionResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to ExceptionResponseDto.class.");
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), failedResponseDto.getCode());
        Assertions.assertEquals(GeneralConstant.Message.ACCESS_DENIED_EXCEPTION_MESSAGE, failedResponseDto.getMessage());
    }

    @Test
    @DisplayName(value = "Delete Employee (NOT AUTHENTICATED).")
    void failedDeleteEmployeeNotAUTHENTICATED(@Value(value = "${employee.id}") Long id) throws Exception {

        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(id)
                .build();

        String responseString = mockMvc.perform(post(Constant.ApiRoutes.DELETE_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(List.of(employeeDto)))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse().getContentAsString();

        AuthenticationFailedResponseDto failedResponseDto = objectMapper.readValue(responseString, AuthenticationFailedResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to AuthenticationFailedResponseDto.class.");
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), failedResponseDto.getCode());
        Assertions.assertEquals(Constant.ApiRoutes.DELETE_X, failedResponseDto.getPath());
        Assertions.assertEquals(Constant.Message.NOT_AUTHENTICATED_MESSAGE, failedResponseDto.getMessage());
    }

    @Test
    @DisplayName(value = "Edit Employee.")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void successEditEmployee(@Value(value = "${employee.id}") Long id) throws Exception {

        final String newLastName = "Updated";

        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(id)
                .lastName(newLastName)
                .build();

        Assertions.assertNotEquals(newLastName, employeeService.getEmployeeById(id).getLastName());

        String responseString = mockMvc.perform(post(Constant.ApiRoutes.EDIT_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(List.of(employeeDto)))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn().getResponse().getContentAsString();

        List<UpdatedEmployeeDto> updatedEmployeeDtoList = objectMapper.readValue(responseString, List.class);
        Assertions.assertNotNull(updatedEmployeeDtoList, "Response can't be deserialized to DeletedEmployeeDto.class.");

        Assertions.assertEquals(newLastName, employeeService.getEmployeeById(id).getLastName());
    }

    @Test
    @DisplayName(value = "Edit Employee (NOT AUTHENTICATED).")
    void failedEditEmployeeNotAuthenticated(@Value(value = "${employee.id}") Long id) throws Exception {

        final String newLastName = "Updated";

        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(id)
                .lastName(newLastName)
                .build();

        String responseString = mockMvc.perform(post(Constant.ApiRoutes.EDIT_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(List.of(employeeDto)))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse().getContentAsString();

        AuthenticationFailedResponseDto failedResponseDto = objectMapper.readValue(responseString, AuthenticationFailedResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to AuthenticationFailedResponseDto.class.");
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), failedResponseDto.getCode());
        Assertions.assertEquals(Constant.ApiRoutes.EDIT_X, failedResponseDto.getPath());
        Assertions.assertEquals(Constant.Message.NOT_AUTHENTICATED_MESSAGE, failedResponseDto.getMessage());
    }

    @Test
    @DisplayName(value = "Edit Employee (ACCESS DENIED).")
    @WithMockUser(authorities = {"ROLE_USER"})
    void failedEditEmployeeNotCompliantAuthorities(@Value(value = "${employee.id}") Long id) throws Exception {

        final String newLastName = "Updated";

        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(id)
                .lastName(newLastName)
                .build();

        String responseString = mockMvc.perform(post(Constant.ApiRoutes.EDIT_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(List.of(employeeDto)))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn().getResponse().getContentAsString();

        ExceptionResponseDto failedResponseDto = objectMapper.readValue(responseString, ExceptionResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to ExceptionResponseDto.class.");
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), failedResponseDto.getCode());
        Assertions.assertEquals(GeneralConstant.Message.ACCESS_DENIED_EXCEPTION_MESSAGE, failedResponseDto.getMessage());
    }

    @Test
    @DisplayName(value = "Add Employee.")
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

        mockMvc.perform(post(Constant.ApiRoutes.ADD_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(List.of(registrationDto)))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(1, employeeService.getAllEmployees().size());
    }

    @Test
    @DisplayName(value = "Add Employee (NOT AUTHENTICATED).")
    void failedAddEmployeeNotAuthenticated() throws Exception {

        RegistrationDto registrationDto = RegistrationDto.builder()
                .userDto(RegistrationUserDto.builder()
                        .build())
                .employeeDto(RegistrationEmployeeDto.builder()
                        .build())
                .build();

        String responseString = mockMvc.perform(post(Constant.ApiRoutes.ADD_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(List.of(registrationDto)))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse().getContentAsString();

        AuthenticationFailedResponseDto failedResponseDto = objectMapper.readValue(responseString, AuthenticationFailedResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to AuthenticationFailedResponseDto.class.");
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), failedResponseDto.getCode());
        Assertions.assertEquals(Constant.ApiRoutes.ADD_X, failedResponseDto.getPath());
        Assertions.assertEquals(Constant.Message.NOT_AUTHENTICATED_MESSAGE, failedResponseDto.getMessage());
    }

    @Test
    @DisplayName(value = "Add Employee (ACCESS DENIED).")
    @WithMockUser(authorities = {"ROLE_USER"})
    void failedAddEmployeeNotCompliantAuthorities() throws Exception {

        RegistrationDto registrationDto = RegistrationDto.builder()
                .userDto(RegistrationUserDto.builder()
                        .build())
                .employeeDto(RegistrationEmployeeDto.builder()
                        .build())
                .build();

        String responseString = mockMvc.perform(post(Constant.ApiRoutes.ADD_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(List.of(registrationDto)))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn().getResponse().getContentAsString();

        ExceptionResponseDto failedResponseDto = objectMapper.readValue(responseString, ExceptionResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to ExceptionResponseDto.class.");
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), failedResponseDto.getCode());
        Assertions.assertEquals(GeneralConstant.Message.ACCESS_DENIED_EXCEPTION_MESSAGE, failedResponseDto.getMessage());
    }

    @Test
    @DisplayName(value = "Add Employee (ACCESS DENIED).")
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

        String responseString = mockMvc.perform(post(Constant.ApiRoutes.ADD_X)
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
