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
public class DeletedEmployeeDto {

    private String firstName;

    private String middleName;

    private String lastName;

    private Date hireDate;

    private boolean isDeleted;
}
