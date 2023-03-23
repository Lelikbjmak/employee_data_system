package com.innowise.employeedatasystem.service;

import com.innowise.employeedatasystem.dto.*;

import java.util.List;

public interface EmployeeManagementService {

    RegistrationResponseDto registerEmployeeList(List<RegistrationDto> registrationRequestDto);

    RegistrationResponseDto registerEmployee(RegistrationDto registrationDto);

    List<EmployeeDto> getAllEmployees();

    List<UpdatedEmployeeDto> editEmployeeList(List<EmployeeDto> editEmployeeDtoList);

    UpdatedEmployeeDto editEmployee(Long employeeToEditId, EmployeeDto editedEmployeeDto);

    List<DeletedEmployeeDto> deleteEmployeeList(List<Long> deleteEmployeeIdList);

    DeletedEmployeeDto deleteEmployee(Long employeeId);

    List<EmployeeDto> getEmployeeListByUserUsernameList(List<String> usernameList);

    EmployeeDto getEmployeeByUserUsername(String username);

    List<EmployeeDto> getEmployeeListByIdList(List<Long> idList);

    EmployeeDto getEmployeeById(Long id);
}
