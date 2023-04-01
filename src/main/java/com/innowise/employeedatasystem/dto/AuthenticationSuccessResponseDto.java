package com.innowise.employeedatasystem.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.innowise.employeedatasystem.util.DtoConstant;
import lombok.Builder;

import java.util.Date;

@Builder
@JsonPropertyOrder({
        DtoConstant.Json.TIMESTAMP_FIELD_NAME,
        DtoConstant.Json.STATUS_FIELD_NAME,
        DtoConstant.Json.CODE_FIELD_NAME,
        DtoConstant.Json.MESSAGE_FIELD_NAME,
        DtoConstant.Json.TOKEN_FIELD_NAME})
public record AuthenticationSuccessResponseDto(Date timestamp, int code, String status, String message, String token) {
}
