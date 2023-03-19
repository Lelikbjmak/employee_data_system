package com.innowise.employeedatasystem.dto;


import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegistrationResponseDto {

    private Date timestamp;

    private int code;

    private String status;

    private String message;

    private List<EmployeeDto> content;
}
