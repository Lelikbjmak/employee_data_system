package com.innowise.employeedatasystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.innowise.employeedatasystem.util.DtoConstant;
import com.innowise.employeedatasystem.util.EntityConstant;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {

    @Valid
    @NotNull(message = EntityConstant.Validation.User.USER_MANDATORY_FOR_REGISTRATION_CONSTRAINT_MESSAGE)
    @JsonProperty(value = DtoConstant.Json.JSON_USER_NAME)
    private RegistrationUserDto userDto;

    @Valid
    @NotNull(message = EntityConstant.Validation.Employee.EMPLOYEE_MANDATORY_FOR_REGISTRATION_CONSTRAINT_MESSAGE)
    @JsonProperty(value = DtoConstant.Json.JSON_EMPLOYEE_NAME)
    private RegistrationEmployeeDto employeeDto;
}
