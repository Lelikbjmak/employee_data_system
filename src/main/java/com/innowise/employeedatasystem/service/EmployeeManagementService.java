package com.innowise.employeedatasystem.service;

import com.innowise.employeedatasystem.dto.*;

import java.util.List;

public interface EmployeeManagementService {

    RegistrationResponseDto registerEmployees(List<RegistrationDto> registrationRequestDto);

    List<EmployeeDto> getAllEmployees();

    List<UpdatedEmployeeDto> editEmployees(List<EmployeeDto> editEmployeeDtoList);

    List<DeletedEmployeeDto> deleteEmployees(List<EmployeeDto> deleteEmployeeDtoList);

    EmployeeDto getEmployeeByUserUsername(String username);

    EmployeeDto getEmployeeById(Long id);
}
