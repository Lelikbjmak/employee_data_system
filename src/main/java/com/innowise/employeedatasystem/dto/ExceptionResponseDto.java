package com.innowise.employeedatasystem.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "timestamp", "status", "code", "message", "path"})
public class ExceptionResponseDto {

    private Instant timestamp;

    private int code;

    private String status;

    private String message;

    private Map<String, Object> additional;
}
