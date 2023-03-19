package com.innowise.employeedatasystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {

    private RegistrationUserDto userDto;

    private RegistrationEmployeeDto employeeDto;
}
