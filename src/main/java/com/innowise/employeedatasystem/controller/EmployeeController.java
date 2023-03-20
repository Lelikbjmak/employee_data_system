package com.innowise.employeedatasystem.controller;

import com.innowise.employeedatasystem.dto.*;
import com.innowise.employeedatasystem.exception.EmployeeIsNotFoundException;
import com.innowise.employeedatasystem.serviceimpl.EmployeeManagementServiceImpl;
import com.innowise.employeedatasystem.util.ApiConstant;
import com.innowise.employeedatasystem.util.GeneralConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = ApiConstant.ApiMappings.EMPLOYEE_ROUT)
public class EmployeeController {

    private final EmployeeManagementServiceImpl employeeManagementService;

    @PostMapping(value = ApiConstant.ApiPath.ADD_X)
    @PreAuthorize(ApiConstant.Security.HAS_ROLE_ADMIN)
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationResponseDto addEmployee(@RequestBody List<RegistrationDto> registrationDto) {
        return employeeManagementService.registerEmployees(registrationDto);
    }

    @PostMapping(value = ApiConstant.ApiPath.EDIT_X)
    @PreAuthorize(ApiConstant.Security.HAS_ROLE_ADMIN)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<UpdatedEmployeeDto> editEmployee(@RequestBody List<EmployeeDto> editEmployeeDtoList) {
        return employeeManagementService.editEmployees(editEmployeeDtoList);
    }

    @PostMapping(value = ApiConstant.ApiPath.DELETE_X)
    @PreAuthorize(ApiConstant.Security.HAS_ROLE_ADMIN)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<DeletedEmployeeDto> deleteEmployees(@RequestBody List<EmployeeDto> deleteEmployeeDtoList) {
        return employeeManagementService.deleteEmployees(deleteEmployeeDtoList);
    }

    @GetMapping(value = ApiConstant.ApiPath.ALL_X)
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> getAll() {
        return employeeManagementService.getAllEmployees();
    }

    @GetMapping(value = ApiConstant.ApiPath.GET_X_BY_ID)
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto getEmployeeById(@PathVariable(name = GeneralConstant.Field.ID_FIELD) Long id) throws EmployeeIsNotFoundException {
        return employeeManagementService.getEmployeeById(id);
    }

    @GetMapping(value = ApiConstant.ApiPath.GET_X)
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto getEmployeeByUserUsername(@RequestParam(name = GeneralConstant.Field.USERNAME_FIELD) String username) {
        return employeeManagementService.getEmployeeByUserUsername(username);
    }

}
