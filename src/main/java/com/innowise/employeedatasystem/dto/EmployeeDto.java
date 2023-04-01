package com.innowise.employeedatasystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.innowise.employeedatasystem.util.DtoConstant;
import com.innowise.employeedatasystem.util.EntityConstant;
import lombok.Builder;

import java.util.Date;

@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonRootName(value = DtoConstant.Employee.EMPLOYEE_JSON_ROOT_NAME)
@JsonPropertyOrder({EntityConstant.Column.ID_FIELD,
        EntityConstant.Column.EMPLOYEE_FIRST_NAME_FIELD,
        EntityConstant.Column.EMPLOYEE_LAST_NAME_FIELD,
        EntityConstant.Column.EMPLOYEE_MIDDLE_NAME_FIELD,
        EntityConstant.Column.EMPLOYEE_HIRE_DATE_FIELD,
        DtoConstant.Json.JSON_USER_DTO_NAME})
public record EmployeeDto(
        Long id, String firstName, String middleName, String lastName, Date hireDate, UserDto userDto) {
}
