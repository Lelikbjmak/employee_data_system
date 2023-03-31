package com.innowise.employeedatasystem.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.innowise.employeedatasystem.util.EntityConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = EntityConstant.Column.EMPLOYEE)
public class RegistrationEmployeeDto {

    @NotBlank(message = EntityConstant.Validation.Employee.FIRST_NAME_MANDATORY_CONSTRAINT_MESSAGE)
    private String firstName;

    @NotBlank(message = EntityConstant.Validation.Employee.LAST_NAME_MANDATORY_CONSTRAINT_MESSAGE)
    private String middleName;

    @NotBlank(message = EntityConstant.Validation.Employee.MIDDLE_NAME_MANDATORY_CONSTRAINT_MESSAGE)
    private String lastName;

    @NotNull(message = EntityConstant.Validation.Employee.HIRE_DATE_MANDATORY_CONSTRAINT_MESSAGE)
    @PastOrPresent(message = EntityConstant.Validation.Employee.HIRE_DATE_EXCEPTION_MESSAGE)
    private Date hireDate;
}
