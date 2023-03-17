package com.innowise.employeedatasystem.dto;

import lombok.*;

import java.util.Date;
import java.util.Map;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationFailedResponseDto {

    private Date timestamp;

    private int code;

    private String status;

    private String message;

    private String path;

    private Map<String, Object> additional;
}
