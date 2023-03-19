package com.innowise.employeedatasystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationEmployeeDto {

    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private Date hireDate;
}
