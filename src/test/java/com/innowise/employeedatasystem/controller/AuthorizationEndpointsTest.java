package com.innowise.employeedatasystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.employeedatasystem.EmployeeDataSystemApplication;
import com.innowise.employeedatasystem.dto.EmployeeDto;
import com.innowise.employeedatasystem.dto.ExceptionResponseDto;
import com.innowise.employeedatasystem.dto.RegistrationDto;
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
class AuthorizationEndpointsTest {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Autowired
    public AuthorizationEndpointsTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    @DisplayName(value = "Context loads")
    void contextLoads() {
        Assertions.assertNotNull(objectMapper);
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    @DisplayName(value = "Delete Employee (ACCESS DENIED).")
    @WithMockUser(authorities = {"ROLE_USER"})
    void failedDeleteEmployeeNotCompliantAuthorities(@Value(value = "${employee.id}") Long id) throws Exception {

        String responseString = mockMvc.perform(delete(Constant.ApiRoutes.DELETE_X + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
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
    @DisplayName(value = "Delete Employee List (ACCESS DENIED).")
    @WithMockUser(authorities = {"ROLE_USER"})
    void failedDeleteEmployeeListNotCompliantAuthorities() throws Exception {

        String responseString = mockMvc.perform(delete(Constant.ApiRoutes.DELETE_ALL_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(List.of())))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn().getResponse().getContentAsString();

        ExceptionResponseDto failedResponseDto = objectMapper.readValue(responseString, ExceptionResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to ExceptionResponseDto.class.");
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), failedResponseDto.getCode());
        Assertions.assertEquals(GeneralConstant.Message.ACCESS_DENIED_EXCEPTION_MESSAGE, failedResponseDto.getMessage());
    }

    @Test
    @DisplayName(value = "Edit Employee (ACCESS DENIED).")
    @WithMockUser(authorities = {"ROLE_USER"})
    void failedEditEmployeeNotCompliantAuthorities(@Value(value = "${employee.id}") Long id) throws Exception {

        String responseString = mockMvc.perform(put(Constant.ApiRoutes.EDIT_X + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new EmployeeDto()))
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
    @DisplayName(value = "Edit Employee List (ACCESS DENIED).")
    @WithMockUser(authorities = {"ROLE_USER"})
    void failedEditEmployeeListNotCompliantAuthorities() throws Exception {

        String responseString = mockMvc.perform(put(Constant.ApiRoutes.EDIT_ALL_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(List.of()))
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
    @WithMockUser(authorities = {"ROLE_USER"})
    void failedAddEmployeeNotCompliantAuthorities() throws Exception {

        String responseString = mockMvc.perform(post(Constant.ApiRoutes.ADD_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new RegistrationDto()))
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
    @DisplayName(value = "Add Employee List (ACCESS DENIED).")
    @WithMockUser(authorities = {"ROLE_USER"})
    void failedAddEmployeeListNotCompliantAuthorities() throws Exception {

        String responseString = mockMvc.perform(post(Constant.ApiRoutes.ADD_ALL_X)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(List.of()))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn().getResponse().getContentAsString();

        ExceptionResponseDto failedResponseDto = objectMapper.readValue(responseString, ExceptionResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to ExceptionResponseDto.class.");
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), failedResponseDto.getCode());
        Assertions.assertEquals(GeneralConstant.Message.ACCESS_DENIED_EXCEPTION_MESSAGE, failedResponseDto.getMessage());
    }
}
