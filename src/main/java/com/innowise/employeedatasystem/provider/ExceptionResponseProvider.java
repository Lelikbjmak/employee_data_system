package com.innowise.employeedatasystem.provider;

import com.innowise.employeedatasystem.dto.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Component
public class ExceptionResponseProvider {

    public ExceptionResponseDto getExceptionResponse(HttpStatus status, String message, Map<String, Object> content) {
        return ExceptionResponseDto.builder()
                .timestamp(Instant.now())
                .status(status.name())
                .code(status.value())
                .message(message)
                .additional(content)
                .build();
    }
}
