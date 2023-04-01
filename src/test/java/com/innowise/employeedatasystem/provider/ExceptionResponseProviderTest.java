package com.innowise.employeedatasystem.provider;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = ExceptionResponseProvider.class)
class ExceptionResponseProviderTest {

    @Autowired
    private ExceptionResponseProvider responseProvider;

    @Test
    @DisplayName(value = "Context loads")
    void contextLoads() {
        Assertions.assertNotNull(responseProvider);
    }

    @Test
    @DisplayName(value = "generate RegistrationResponseDto")
    void mustReturnRegistrationResponseDto() {
        Assertions.assertNotNull(responseProvider.getExceptionResponse(HttpStatus.CREATED, Mockito.anyString(), Mockito.anyMap()));
    }
}