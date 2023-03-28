package com.innowise.employeedatasystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {

    @Valid
    @JsonProperty(value = "user")
    private RegistrationUserDto userDto;

    @Valid
    @JsonProperty(value = "employee")
    private RegistrationEmployeeDto employeeDto;
}
