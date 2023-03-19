package com.innowise.employeedatasystem.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "timestamp", "status", "code", "message", "path"})
public class AccessDeniedResponseDto {

    private Date timestamp;

    private int code;

    private String status;

    private String message;

    private String path;
}
