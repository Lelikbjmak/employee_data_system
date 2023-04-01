package com.innowise.employeedatasystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.employeedatasystem.EmployeeDataSystemApplication;
import com.innowise.employeedatasystem.dto.AuthenticationFailedResponseDto;
import com.innowise.employeedatasystem.dto.EmployeeDto;
import com.innowise.employeedatasystem.dto.RegistrationDto;
import com.innowise.employeedatasystem.util.Constant;
import com.innowise.employeedatasystem.util.EntityConstant;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

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
class AuthenticationEndpointsTest {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Autowired
    public AuthenticationEndpointsTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    @DisplayName(value = "Get Employee by Id (NOT AUTHENTICATED).")
    void failedGetEmployeeByIdNotAuthenticated(@Value(value = "${employee.id}") Long id) throws Exception {

        String responseString = mockMvc.perform(get(Constant.ApiRoutes.GET_X_BY_ID, id)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse().getContentAsString();

        AuthenticationFailedResponseDto failedResponseDto = objectMapper.readValue(responseString, AuthenticationFailedResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to AuthenticationFailedResponseDto.class.");
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), failedResponseDto.code());
        Assertions.assertEquals(Constant.Message.NOT_AUTHENTICATED_MESSAGE, failedResponseDto.message());
    }

    @Test
    @DisplayName(value = "Delete Employee (NOT AUTHENTICATED).")
    void failedDeleteEmployeeNotAUTHENTICATED(@Value(value = "${employee.id}") Long id) throws Exception {

        String responseString = mockMvc.perform(delete(Constant.ApiRoutes.DELETE_X, id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse().getContentAsString();

        AuthenticationFailedResponseDto failedResponseDto = objectMapper.readValue(responseString, AuthenticationFailedResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to AuthenticationFailedResponseDto.class.");
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), failedResponseDto.code());
        Assertions.assertEquals(Constant.Message.NOT_AUTHENTICATED_MESSAGE, failedResponseDto.message());
    }

    @Test
    @DisplayName(value = "Delete Employee List (NOT AUTHENTICATED).")
    void failedDeleteEmployeeListNotAUTHENTICATED() throws Exception {

        String responseString = mockMvc.perform(delete(Constant.ApiRoutes.DELETE_ALL_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(List.of())))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse().getContentAsString();

        AuthenticationFailedResponseDto failedResponseDto = objectMapper.readValue(responseString, AuthenticationFailedResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to AuthenticationFailedResponseDto.class.");
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), failedResponseDto.code());
        Assertions.assertEquals(Constant.Message.NOT_AUTHENTICATED_MESSAGE, failedResponseDto.message());
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
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), failedResponseDto.code());
        Assertions.assertEquals(Constant.ApiRoutes.GET_ALL_X, failedResponseDto.path());
        Assertions.assertEquals(Constant.Message.NOT_AUTHENTICATED_MESSAGE, failedResponseDto.message());
    }

    @Test
    @DisplayName(value = "Get Employee by username (NOT AUTHENTICATED).")
    void failedGetEmployeeByUserUsernameNotAuthenticated(@Value(value = "${user.username}") String username) throws Exception {

        String responseString = mockMvc.perform(get(Constant.ApiRoutes.GET_X_BY_USERNAME, username)
                        .param(EntityConstant.Column.USERNAME_FIELD, username)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse().getContentAsString();

        AuthenticationFailedResponseDto failedResponseDto = objectMapper.readValue(responseString, AuthenticationFailedResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to AuthenticationFailedResponseDto.class.");
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), failedResponseDto.code());
        Assertions.assertEquals(Constant.Message.NOT_AUTHENTICATED_MESSAGE, failedResponseDto.message());
    }

    @Test
    @DisplayName(value = "Edit Employee (NOT AUTHENTICATED).")
    void failedEditEmployeeNotAuthenticated(@Value(value = "${employee.id}") Long id) throws Exception {

        String responseString = mockMvc.perform(put(Constant.ApiRoutes.EDIT_X, id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse().getContentAsString();

        AuthenticationFailedResponseDto failedResponseDto = objectMapper.readValue(responseString, AuthenticationFailedResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to AuthenticationFailedResponseDto.class.");
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), failedResponseDto.code());
        Assertions.assertEquals(Constant.Message.NOT_AUTHENTICATED_MESSAGE, failedResponseDto.message());
    }

    @Test
    @DisplayName(value = "Edit Employee List (NOT AUTHENTICATED).")
    void failedEditEmployeeListNotAuthenticated() throws Exception {

        String responseString = mockMvc.perform(put(Constant.ApiRoutes.EDIT_ALL_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(EmployeeDto.builder().build()))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse().getContentAsString();

        AuthenticationFailedResponseDto failedResponseDto = objectMapper.readValue(responseString, AuthenticationFailedResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to AuthenticationFailedResponseDto.class.");
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), failedResponseDto.code());
        Assertions.assertEquals(Constant.Message.NOT_AUTHENTICATED_MESSAGE, failedResponseDto.message());
    }

    @Test
    @DisplayName(value = "Add Employee List (NOT AUTHENTICATED).")
    void failedAddEmployeeListNotAuthenticated() throws Exception {

        String responseString = mockMvc.perform(post(Constant.ApiRoutes.ADD_ALL_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(List.of(RegistrationDto.builder().build())))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse().getContentAsString();

        AuthenticationFailedResponseDto failedResponseDto = objectMapper.readValue(responseString, AuthenticationFailedResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to AuthenticationFailedResponseDto.class.");
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), failedResponseDto.code());
        Assertions.assertEquals(Constant.Message.NOT_AUTHENTICATED_MESSAGE, failedResponseDto.message());
    }

    @Test
    @DisplayName(value = "Add Employee (NOT AUTHENTICATED).")
    void failedAddEmployeeNotAuthenticated() throws Exception {

        String responseString = mockMvc.perform(post(Constant.ApiRoutes.ADD_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(RegistrationDto.builder().build()))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse().getContentAsString();

        AuthenticationFailedResponseDto failedResponseDto = objectMapper.readValue(responseString, AuthenticationFailedResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to AuthenticationFailedResponseDto.class.");
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), failedResponseDto.code());
        Assertions.assertEquals(Constant.Message.NOT_AUTHENTICATED_MESSAGE, failedResponseDto.message());
    }
}
