package com.innowise.employeedatasystem.provider;

import com.innowise.employeedatasystem.dto.ExceptionResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = ResponseEntityProvider.class)
class ResponseEntityProviderTest {

    @Autowired
    private ResponseEntityProvider<ExceptionResponseDto> entityProvider;

    @Test
    @DisplayName(value = "Context loads")
    void contextLoads() {
        Assertions.assertNotNull(entityProvider);
    }

    @Test
    @DisplayName(value = "generate RegistrationResponseDto")
    void mustReturnRegistrationResponseDto() {
        ResponseEntity<ExceptionResponseDto> response = entityProvider.generateResponseEntity(ExceptionResponseDto.builder().build(), HttpStatus.OK);
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }
}