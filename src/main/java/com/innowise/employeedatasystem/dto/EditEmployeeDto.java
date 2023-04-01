package com.innowise.employeedatasystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.innowise.employeedatasystem.util.DtoConstant;
import com.innowise.employeedatasystem.util.EntityConstant;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Date;

@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonRootName(value = DtoConstant.Employee.EMPLOYEE_JSON_ROOT_NAME)
public record EditEmployeeDto(
        @NotNull(message = EntityConstant.Validation.Employee.ID_MANDATORY_TO_UPDATE_CONSTRAINT_MESSAGE) Long id,
        String firstName, String middleName, String lastName, Date hireDate) {

}
