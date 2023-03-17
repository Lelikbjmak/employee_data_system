package com.innowise.employeedatasystem.service;

import com.innowise.employeedatasystem.dto.EmployeeDto;
import com.innowise.employeedatasystem.dto.RegistrationDto;
import com.innowise.employeedatasystem.dto.RegistrationResponseDto;

import java.util.List;

public interface EmployeeManagementService {

    RegistrationResponseDto registerEmployees(List<RegistrationDto> registrationRequestDto);

    List<EmployeeDto> getAllEmployees();
}
