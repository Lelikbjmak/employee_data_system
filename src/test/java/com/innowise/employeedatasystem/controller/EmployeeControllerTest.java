package com.innowise.employeedatasystem.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.employeedatasystem.EmployeeDataSystemApplication;
import com.innowise.employeedatasystem.dto.*;
import com.innowise.employeedatasystem.entity.RoleEnum;
import com.innowise.employeedatasystem.exception.EmployeeIsNotFoundException;
import com.innowise.employeedatasystem.serviceimpl.EmployeeServiceImpl;
import com.innowise.employeedatasystem.util.*;
import org.junit.jupiter.api.*;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    @Order(1)
    @DisplayName(value = "Context loads")
    void contextLoads() {
        Assertions.assertNotNull(objectMapper);
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    @Order(2)
    @DisplayName(value = "`Get` Employee by Id.")
    @WithMockUser(authorities = {"ROLE_USER"})
    void successGetEmployeeById(@Value(value = "${employee.id}") Long id) throws Exception {

        String responseString = mockMvc.perform(get(Constant.ApiRoutes.GET_X_BY_ID, 1L)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        EmployeeDto employeeDto = objectMapper.readValue(responseString, EmployeeDto.class);
        Assertions.assertNotNull(employeeDto, "Response can't be deserialized to EmployeeDto.class.");
        Assertions.assertEquals(id, employeeDto.id());
    }

    @Order(3)
    @DisplayName(value = "`Get` Employee by Id (EMPLOYEE NOT FOUND).")
    @WithMockUser(authorities = {"ROLE_USER"})
    void failedGetEmployeeById() throws Exception {

        final long invalidId = 100L;

        String responseString = mockMvc.perform(get(Constant.ApiRoutes.GET_X_BY_ID + "/" + invalidId)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        ExceptionResponseDto failedResponseDto = objectMapper.readValue(responseString, ExceptionResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to ExceptionResponseDto.class.");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), failedResponseDto.code());
        Assertions.assertEquals(GeneralConstant.Message.EMPLOYEE_IS_NOT_FOUND_BY_ID_EXCEPTION_MESSAGE, failedResponseDto.message());
    }

    @Test
    @Order(4)
    @DisplayName(value = "`Get` Employee by username.")
    @WithMockUser(authorities = {"ROLE_USER"})
    void successGetEmployeeByUserUsername(@Value(value = "${user.username}") String username) throws Exception {

        String responseString = mockMvc.perform(get(Constant.ApiRoutes.GET_X_BY_USERNAME, username)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        EmployeeDto employeeDto = objectMapper.readValue(responseString, EmployeeDto.class);
        Assertions.assertNotNull(employeeDto, "Response can't be deserialized to EmployeeDto.class.");
        Assertions.assertEquals(username, employeeDto.userDto().username());
    }

    @Test
    @Order(5)
    @DisplayName(value = "`Get` Employee by username (EMPLOYEE NOT FOUND).")
    @WithMockUser(authorities = {"ROLE_USER"})
    void failedGetEmployeeByUserUsernameEmployeeNotFound() throws Exception {

        final String failedUsername = "wrongUsername";

        String responseString = mockMvc.perform(get(Constant.ApiRoutes.GET_X_BY_USERNAME, failedUsername)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        ExceptionResponseDto failedResponseDto = objectMapper.readValue(responseString, ExceptionResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to ExceptionResponseDto.class.");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), failedResponseDto.code());
        Assertions.assertEquals(GeneralConstant.Message.EMPLOYEE_IS_NOT_FOUND_BY_ID_EXCEPTION_MESSAGE, failedResponseDto.message());
    }

    @Test
    @Order(6)
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
    @Order(7)
    @DisplayName(value = "`Delete` Employee.")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void successDeleteEmployee(@Value(value = "${employee.id}") Long id) throws Exception {

        Assertions.assertNotNull(employeeService.getEmployeeById(id));

        String responseString = mockMvc.perform(delete(Constant.ApiRoutes.DELETE_X, id)
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
    @Order(8)
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
    @Order(9)
    @DisplayName(value = "`Edit` Employee List.")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void successEditEmployeeList() throws Exception {

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
    @Order(10)
    @DisplayName(value = "`Edit` Employee.")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void successEditEmployee(@Value(value = "${employee.id}") Long id) throws Exception {

        final String newLastName = "Updated";

        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(id)
                .lastName(newLastName)
                .build();

        Assertions.assertNotEquals(newLastName, employeeService.getEmployeeById(id).getLastName());

        String responseString = mockMvc.perform(put(Constant.ApiRoutes.EDIT_X, id)
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
    @Order(11)
    @DisplayName(value = "`Edit` Employee (FAILED ID LESS THAT ZERO).")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void failedEditEmployeeDto(@Value(value = "${employee.id}") Long id) throws Exception {

        final String newLastName = "Updated";

        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(id)
                .lastName(newLastName)
                .build();

        Assertions.assertNotEquals(newLastName, employeeService.getEmployeeById(id).getLastName());

        String responseString = mockMvc.perform(put(Constant.ApiRoutes.EDIT_X, -1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employeeDto))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        Assertions.assertTrue(responseString.contains("editEmployee.id"));
        Assertions.assertTrue(responseString.contains("Id must be positive number"));
    }

    @Test
    @Order(12)
    @DisplayName(value = "`Add` Employee.")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/before-registration.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void successAddEmployee(
            @Value(value = "${user.mail}") String mail, @Value(value = "${employee.first-name}") String firstName,
            @Value(value = "${employee.last-name}") String lastName, @Value(value = "${employee.middle-name}") String middleName,
            @Value(value = "${user.role-admin}") String roleAdmin) throws Exception {

        RegistrationDto registrationDto = RegistrationDto.builder()
                .userDto(RegistrationUserDto.builder()
                        .username("username")
                        .password("password1A")
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
                        .content(objectMapper.writeValueAsString(registrationDto))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(1, employeeService.getAllEmployees().size());
    }

    @Test
    @Order(13)
    @DisplayName(value = "`Add` Employee (ROLE IS NOT FOUND).")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void failedAddEmployeeInvalidRole() throws Exception {

        RegistrationDto registrationDto = RegistrationDto.builder()
                .userDto(RegistrationUserDto.builder()
                        .username("username")
                        .roles(Set.of("INVALID"))
                        .mail("mail@gmail.com")
                        .password("password1A")
                        .build())
                .employeeDto(RegistrationEmployeeDto.builder()
                        .firstName("firstName")
                        .lastName("middleName")
                        .middleName("firstName")
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
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), failedResponseDto.code());
        Assertions.assertEquals(GeneralConstant.Message.ROLE_IS_NOT_FOUND_EXCEPTION_MESSAGE, failedResponseDto.message());
    }

    @Test
    @Order(14)
    @DisplayName(value = "`Add` Employee List.")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/before-registration.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void successAddEmployeeList(
            @Value(value = "${user.mail}") String mail, @Value(value = "${employee.first-name}") String firstName,
            @Value(value = "${employee.last-name}") String lastName, @Value(value = "${employee.middle-name}") String middleName,
            @Value(value = "${user.role-admin}") String roleAdmin) throws Exception {

        RegistrationDto registrationDto = RegistrationDto.builder()
                .userDto(RegistrationUserDto.builder()
                        .username("username")
                        .password("password1A")
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
    @Order(15)
    @DisplayName(value = "`Add` Employee List (ROLE IS NOT FOUND).")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/before-registration.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void failedAddEmployeeListInvalidRole(
            @Value(value = "${user.mail}") String mail, @Value(value = "${employee.first-name}") String firstName,
            @Value(value = "${employee.last-name}") String lastName, @Value(value = "${employee.middle-name}") String middleName) throws Exception {

        RegistrationDto registrationDto = RegistrationDto.builder()
                .userDto(RegistrationUserDto.builder()
                        .username("username")
                        .password("password1A")
                        .mail(mail)
                        .roles(Set.of("INVALID"))
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
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(0, employeeService.getAllEmployees().size());
    }

    @Test
    @Order(16)
    @DisplayName(value = "`Add` Employee List (UserDto & EmployeeDto fields are blank).")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/before-registration.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void failedAddEmployeeListDtoHasBlankFields() throws Exception {

        RegistrationDto registrationDto = RegistrationDto.builder()
                .userDto(RegistrationUserDto.builder()
                        .username("")
                        .password("")
                        .mail("")
                        .roles(Set.of(RoleEnum.ROLE_USER.name()))
                        .build())
                .employeeDto(RegistrationEmployeeDto.builder()
                        .firstName("")
                        .lastName("")
                        .middleName("")
                        .hireDate(new Date())
                        .build())
                .build();

        String response = mockMvc.perform(post(Constant.ApiRoutes.ADD_ALL_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(List.of(registrationDto)))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        Assertions.assertTrue(response.contains(EntityConstant.Validation.User.USERNAME_MANDATORY_CONSTRAINT_MASSAGE));
        Assertions.assertTrue(response.contains(EntityConstant.Validation.User.PASSWORD_MANDATORY_CONSTRAINT_MASSAGE));
        Assertions.assertTrue(response.contains(EntityConstant.Validation.User.MAIL_NOT_VALID_FORMAT_CONSTRAINT_MESSAGE));

        Assertions.assertTrue(response.contains(EntityConstant.Validation.Employee.FIRST_NAME_MANDATORY_CONSTRAINT_MESSAGE));
        Assertions.assertTrue(response.contains(EntityConstant.Validation.Employee.LAST_NAME_MANDATORY_CONSTRAINT_MESSAGE));
        Assertions.assertTrue(response.contains(EntityConstant.Validation.Employee.MIDDLE_NAME_MANDATORY_CONSTRAINT_MESSAGE));

        Assertions.assertEquals(0, employeeService.getAllEmployees().size());
    }

    @Test
    @Order(17)
    @DisplayName(value = "`Add` Employee (List) (HIRE_DATE AND SET<ROLES> ARE NULL).")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/before-registration.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void failedAddEmployeeListDtoHasRolesAndHireDateAreNull() throws Exception {

        RegistrationDto registrationDto = RegistrationDto.builder()
                .userDto(RegistrationUserDto.builder()
                        .username("username")
                        .password("aaaaaaaA1")
                        .mail("mail@gamil.com")
                        .roles(null)
                        .build())
                .employeeDto(RegistrationEmployeeDto.builder()
                        .firstName("firstName")
                        .lastName("lastName")
                        .middleName("middleName")
                        .hireDate(null)
                        .build())
                .build();

        String response = mockMvc.perform(post(Constant.ApiRoutes.ADD_ALL_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(List.of(registrationDto)))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(0, employeeService.getAllEmployees().size());
    }

    @Test
    @Order(18)
    @DisplayName(value = "`Add` Employee List (NAT VALID USERNAME AND PASSWORD (INCORRECT LENGTH)).")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/before-registration.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void failedAddEmployeeListNotValidUsernameAndPasswordLength() throws Exception {

        RegistrationDto registrationDto = RegistrationDto.builder()
                .userDto(RegistrationUserDto.builder()
                        .username("1")
                        .password("1")
                        .mail("mail@gmail.com")
                        .roles(Set.of(RoleEnum.ROLE_USER.name()))
                        .build())
                .employeeDto(RegistrationEmployeeDto.builder()
                        .firstName("")
                        .lastName("")
                        .middleName("")
                        .hireDate(new Date())
                        .build())
                .build();

        String response = mockMvc.perform(post(Constant.ApiRoutes.ADD_ALL_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(List.of(registrationDto)))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        Assertions.assertTrue(response.contains(EntityConstant.Validation.User.USERNAME_LENGTH_CONSTRAINT_MASSAGE));
        Assertions.assertTrue(response.contains(EntityConstant.Validation.User.PASSWORD_LENGTH_CONSTRAINT_MASSAGE));

        Assertions.assertEquals(0, employeeService.getAllEmployees().size());
    }

    @Test
    @Order(19)
    @DisplayName(value = "`Add` Employee List (NAT VALID USERNAME AND PASSWORD (INCORRECT FORMAT)).")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/before-registration.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void failedAddEmployeeListNotValidUsernameAndPasswordFormat() throws Exception {

        RegistrationDto registrationDto = RegistrationDto.builder()
                .userDto(RegistrationUserDto.builder()
                        .username("^^^^^^")
                        .password("&$$$$$$$")
                        .mail("mail@gmail.com")
                        .roles(Set.of(RoleEnum.ROLE_USER.name()))
                        .build())
                .employeeDto(RegistrationEmployeeDto.builder()
                        .firstName("")
                        .lastName("")
                        .middleName("")
                        .hireDate(new Date())
                        .build())
                .build();

        String response = mockMvc.perform(post(Constant.ApiRoutes.ADD_ALL_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(List.of(registrationDto)))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        Assertions.assertTrue(response.contains(EntityConstant.Validation.User.USERNAME_NOT_VALID_FORMAT_MESSAGE));
        Assertions.assertTrue(response.contains(EntityConstant.Validation.User.PASSWORD_NOT_VALID_FORMAT_MESSAGE));

        Assertions.assertEquals(0, employeeService.getAllEmployees().size());
    }

    @Test
    @Order(20)
    @DisplayName(value = "`Add` Employee (REQUEST BODY IS MISSED).")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/before-registration.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void requestBodyIsMissed() throws Exception {

        String response = mockMvc.perform(post(Constant.ApiRoutes.ADD_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        Assertions.assertTrue(response.contains(ApiConstant.Validation.REQUEST_BODY_MISSED_MESSAGE));
    }

    @Test
    @Order(21)
    @DisplayName(value = "`Add` Employee (REQUEST BODY IS EMPTY).")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/before-registration.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void requestBodyIsEmpty() throws Exception {

        String response = mockMvc.perform(post(Constant.ApiRoutes.ADD_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{}")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        Assertions.assertTrue(response.contains(GeneralConstant.Message.NOTE_VALID_DATA_EXCEPTION_MESSAGE));
        Assertions.assertTrue(response.contains(DtoConstant.Json.JSON_EMPLOYEE_DTO_NAME));
        Assertions.assertTrue(response.contains(DtoConstant.Json.JSON_USER_DTO_NAME));

        Assertions.assertTrue(response.contains(EntityConstant.Validation.User.USER_MANDATORY_FOR_REGISTRATION_CONSTRAINT_MESSAGE));
        Assertions.assertTrue(response.contains(EntityConstant.Validation.Employee.EMPLOYEE_MANDATORY_FOR_REGISTRATION_CONSTRAINT_MESSAGE));
    }
}
