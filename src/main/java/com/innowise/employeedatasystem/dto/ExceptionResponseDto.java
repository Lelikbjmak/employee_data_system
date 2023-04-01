package com.innowise.employeedatasystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.innowise.employeedatasystem.util.DtoConstant;
import lombok.Builder;

import java.time.Instant;
import java.util.Map;

@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({DtoConstant.Json.TIMESTAMP_FIELD_NAME,
        DtoConstant.Json.STATUS_FIELD_NAME,
        DtoConstant.Json.CODE_FIELD_NAME,
        DtoConstant.Json.MESSAGE_FIELD_NAME,
        DtoConstant.Json.ADDITIONAL_CONTENT_FIELD_NAME})
public record ExceptionResponseDto(
        Instant timestamp, int code, String status, String message, Map<String, Object> additional) {
}
