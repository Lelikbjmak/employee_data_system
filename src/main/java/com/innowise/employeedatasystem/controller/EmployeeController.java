package com.innowise.employeedatasystem.controller;

import com.innowise.employeedatasystem.dto.*;
import com.innowise.employeedatasystem.serviceimpl.EmployeeManagementServiceImpl;
import com.innowise.employeedatasystem.util.ApiConstant;
import com.innowise.employeedatasystem.util.GeneralConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = ApiConstant.ApiMappings.EMPLOYEE_ROUT)
public class EmployeeController {

    private final EmployeeManagementServiceImpl employeeManagementService;

    @PostMapping(value = ApiConstant.ApiPath.ADD_ALL_X)
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationResponseDto addEmployeeList(@RequestBody List<RegistrationDto> registrationDtoList) {
        return employeeManagementService.registerEmployeeList(registrationDtoList);
    }

    @PostMapping(value = ApiConstant.ApiPath.ADD_X)
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationResponseDto addEmployee(@RequestBody RegistrationDto registrationDto) {
        return employeeManagementService.registerEmployee(registrationDto);
    }

    @PutMapping(value = ApiConstant.ApiPath.EDIT_ALL_X)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<UpdatedEmployeeDto> editEmployeeList(@RequestBody List<EmployeeDto> editEmployeeDtoList) {
        return employeeManagementService.editEmployeeList(editEmployeeDtoList);
    }

    @PutMapping(value = ApiConstant.ApiPath.EDIT_X)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UpdatedEmployeeDto editEmployee(@PathVariable(name = GeneralConstant.Field.ID_FIELD) Long id,
                                           @RequestBody EmployeeDto editEmployeeDto) {
        return employeeManagementService.editEmployee(id, editEmployeeDto);
    }

    @DeleteMapping(value = ApiConstant.ApiPath.DELETE_ALL_X)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<DeletedEmployeeDto> deleteEmployeeList(@RequestBody List<Long> deleteEmployeeIdList) {
        return employeeManagementService.deleteEmployeeList(deleteEmployeeIdList);
    }

    @DeleteMapping(value = ApiConstant.ApiPath.DELETE_X)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DeletedEmployeeDto deleteEmployee(@PathVariable(name = GeneralConstant.Field.ID_FIELD) Long id) {
        return employeeManagementService.deleteEmployee(id);
    }

    @GetMapping(value = ApiConstant.ApiPath.ALL_X)
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> getAll() {
        return employeeManagementService.getAllEmployees();
    }

    @GetMapping(value = ApiConstant.ApiPath.GET_X_BY_ID)
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto getEmployeeById(@PathVariable(name = GeneralConstant.Field.ID_FIELD) Long id) {
        return employeeManagementService.getEmployeeById(id);
    }

    @GetMapping(value = ApiConstant.ApiPath.GET_X_BY_USERNAME)
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto getEmployeeByUserUsername(@RequestParam(name = GeneralConstant.Field.USERNAME_FIELD) String username) {
        return employeeManagementService.getEmployeeByUserUsername(username);
    }

}
