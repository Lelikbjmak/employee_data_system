package com.innowise.employeedatasystem.dto;

import lombok.*;

import java.util.Date;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthenticationSuccessResponseDto {

    private Date timestamp;

    private int code;

    private String status;

    private String message;

    private String token;
}
