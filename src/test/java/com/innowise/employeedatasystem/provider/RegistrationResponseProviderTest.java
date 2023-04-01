package com.innowise.employeedatasystem.provider;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = RegistrationResponseProvider.class)
class RegistrationResponseProviderTest {

    @Autowired
    private RegistrationResponseProvider responseProvider;

    @Test
    @DisplayName(value = "Context loads")
    void contextLoads() {
        Assertions.assertNotNull(responseProvider);
    }

    @Test
    @DisplayName(value = "generate RegistrationResponseDto")
    void mustReturnRegistrationResponseDto() {
        Assertions.assertNotNull(responseProvider.generateRegistrationResponse(Mockito.anyList()));
    }
}