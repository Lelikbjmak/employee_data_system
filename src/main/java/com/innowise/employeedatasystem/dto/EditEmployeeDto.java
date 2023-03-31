package com.innowise.employeedatasystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.innowise.employeedatasystem.util.DtoConstant;
import com.innowise.employeedatasystem.util.EntityConstant;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonRootName(value = DtoConstant.Employee.EMPLOYEE_JSON_ROOT_NAME)
public class EditEmployeeDto {

    @NotNull(message = EntityConstant.Validation.Employee.ID_MANDATORY_TO_UPDATE_CONSTRAINT_MESSAGE)
    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private Date hireDate;
}
