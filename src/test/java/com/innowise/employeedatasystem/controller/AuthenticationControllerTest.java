package com.innowise.employeedatasystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.employeedatasystem.EmployeeDataSystemApplication;
import com.innowise.employeedatasystem.dto.AuthenticationFailedResponseDto;
import com.innowise.employeedatasystem.dto.AuthenticationRequestDto;
import com.innowise.employeedatasystem.dto.AuthenticationSuccessResponseDto;
import com.innowise.employeedatasystem.util.Constant;
import com.innowise.employeedatasystem.util.EntityConstant;
import com.innowise.employeedatasystem.util.GeneralConstant;
import org.junit.jupiter.api.*;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {EmployeeDataSystemApplication.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthenticationControllerTest {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Autowired
    public AuthenticationControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
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
    @DisplayName(value = "Successful authentication.")
    @Sql(value = "/sql/create-user-before-authentication.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void successfulAuthenticationTest(@Value(value = "${user.username}") String username,
                                      @Value(value = "${user.password}") String password) throws Exception {

        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto(username, password);

        String responseString = mockMvc.perform(post(Constant.ApiRoutes.AUTH_ROUTE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(authenticationRequestDto))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn().getResponse().getContentAsString();

        AuthenticationSuccessResponseDto successResponseDto = objectMapper.readValue(responseString, AuthenticationSuccessResponseDto.class);
        Assertions.assertNotNull(successResponseDto, "Response can't be deserialized to AuthenticationSuccessResponseDto.class.");
        Assertions.assertEquals(successResponseDto.code(), HttpStatus.ACCEPTED.value());
        Assertions.assertNotNull(successResponseDto.token(), "JWT auth token is not present.");

    }

    @Test
    @Order(3)
    @DisplayName(value = "Failed authentication (User is not found).")
    void failedAuthenticationUserNotFoundTest() throws Exception {

        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto("failedUsername", "wrongPassword");

        String responseString = mockMvc.perform(post(Constant.ApiRoutes.AUTH_ROUTE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(authenticationRequestDto))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse().getContentAsString();

        AuthenticationFailedResponseDto failedResponseDto = objectMapper.readValue(responseString, AuthenticationFailedResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to AuthenticationFailedResponseDto.class.");
        Assertions.assertEquals(failedResponseDto.code(), HttpStatus.UNAUTHORIZED.value());
        Assertions.assertEquals(GeneralConstant.Message.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE, failedResponseDto.message());
    }

    @Test
    @Order(4)
    @DisplayName(value = "Failed authentication (User disabled).")
    @Sql(value = "/sql/create-disabled-user-before-authentication.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void failedAuthenticationAccountDisabledTest(@Value(value = "${user.username}") String username,
                                                 @Value(value = "${user.password}") String password) throws Exception {

        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto(username, password);

        String responseString = mockMvc.perform(post(Constant.ApiRoutes.AUTH_ROUTE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(authenticationRequestDto))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse().getContentAsString();

        AuthenticationFailedResponseDto failedResponseDto = objectMapper.readValue(responseString, AuthenticationFailedResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to AuthenticationFailedResponseDto.class.");
        Assertions.assertEquals(failedResponseDto.code(), HttpStatus.UNAUTHORIZED.value());
        Assertions.assertEquals(GeneralConstant.Message.AUTHENTICATION_ERROR_ACCOUNT_DISABLED_MESSAGE, failedResponseDto.message());
    }

    @Test
    @Order(5)
    @DisplayName(value = "Failed authentication (User expired).")
    @Sql(value = "/sql/create-expired-user-before-authentication.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void failedAuthenticationAccountExpiredTest(@Value(value = "${user.username}") String username,
                                                @Value(value = "${user.password}") String password) throws Exception {

        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto(username, password);

        String responseString = mockMvc.perform(post(Constant.ApiRoutes.AUTH_ROUTE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(authenticationRequestDto))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse().getContentAsString();

        AuthenticationFailedResponseDto failedResponseDto = objectMapper.readValue(responseString, AuthenticationFailedResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to AuthenticationFailedResponseDto.class.");
        Assertions.assertEquals(failedResponseDto.code(), HttpStatus.UNAUTHORIZED.value());
        Assertions.assertEquals(GeneralConstant.Message.AUTHENTICATION_ERROR_ACCOUNT_OR_CREDENTIALS_EXPIRED_MESSAGE, failedResponseDto.message());
    }

    @Test
    @Order(6)
    @DisplayName(value = "Failed authentication (User locked).")
    @Sql(value = "/sql/create-locked-user-before-authentication.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void failedAuthenticationAccountLockedTest(@Value(value = "${user.username}") String username,
                                               @Value(value = "${user.password}") String password) throws Exception {

        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto(username, password);

        String responseString = mockMvc.perform(post(Constant.ApiRoutes.AUTH_ROUTE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(authenticationRequestDto))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse().getContentAsString();

        AuthenticationFailedResponseDto failedResponseDto = objectMapper.readValue(responseString, AuthenticationFailedResponseDto.class);
        Assertions.assertNotNull(failedResponseDto, "Response can't be deserialized to AuthenticationFailedResponseDto.class.");
        Assertions.assertEquals(failedResponseDto.code(), HttpStatus.UNAUTHORIZED.value());
        Assertions.assertEquals(GeneralConstant.Message.AUTHENTICATION_ERROR_ACCOUNT_LOCKED_MESSAGE, failedResponseDto.message());
    }

    @Test
    @Order(7)
    @DisplayName(value = "Failed authentication (Provided username and password are blank).")
    @Sql(value = "/sql/create-locked-user-before-authentication.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/truncate-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void failedAuthenticationNotValidFormOfCredentialsUsernameAndPasswordBlank() throws Exception {

        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto(null, null);

        String responseString = mockMvc.perform(post(Constant.ApiRoutes.AUTH_ROUTE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(authenticationRequestDto))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        Assertions.assertTrue(responseString.contains(EntityConstant.Validation.User.USERNAME_MANDATORY_CONSTRAINT_MASSAGE));
        Assertions.assertTrue(responseString.contains(EntityConstant.Validation.User.PASSWORD_MANDATORY_CONSTRAINT_MASSAGE));
    }
}
