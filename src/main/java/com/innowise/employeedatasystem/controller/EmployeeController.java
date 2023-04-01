package com.innowise.employeedatasystem.controller;

import com.innowise.employeedatasystem.dto.*;
import com.innowise.employeedatasystem.serviceimpl.EmployeeManagementServiceImpl;
import com.innowise.employeedatasystem.util.ApiConstant;
import com.innowise.employeedatasystem.util.EntityConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = ApiConstant.ApiMappings.EMPLOYEE_ROUT)
public class EmployeeController {

    private final EmployeeManagementServiceImpl employeeManagementService;

    @PostMapping(value = ApiConstant.ApiPath.ADD_ALL_X)
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationResponseDto addEmployeeList(@RequestBody @Valid List<RegistrationDto> registrationDtoList,
                                                   HttpServletRequest request) {
        log.info("Request: {} is processing...", request.getRequestURI());
        return employeeManagementService.registerEmployeeList(registrationDtoList);
    }

    @PostMapping(value = ApiConstant.ApiPath.ADD_X)
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationResponseDto addEmployee(@RequestBody @Valid RegistrationDto registrationDto,
                                               HttpServletRequest request) {
        log.info("Request: {} is processing...", request.getRequestURI());
        return employeeManagementService.registerEmployee(registrationDto);
    }

    @PutMapping(value = ApiConstant.ApiPath.EDIT_ALL_X)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<UpdatedEmployeeDto> editEmployeeList(@RequestBody @Valid List<EditEmployeeDto> editEmployeeDtoList,
                                                     HttpServletRequest request) {
        log.info("Request: {} is processing...", request.getRequestURI());
        return employeeManagementService.editEmployeeList(editEmployeeDtoList);
    }

    @PutMapping(value = ApiConstant.ApiPath.EDIT_X)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UpdatedEmployeeDto editEmployee(
            @PathVariable(name = EntityConstant.Column.ID_FIELD)
            @Min(value = 0, message = EntityConstant.Validation.General.ID_MUST_GREATER_THAN_ZERO_CONSTRAINT_MESSAGE) Long id,
            @RequestBody EmployeeDto editEmployeeDto, HttpServletRequest request) {
        log.info("Request: {} is processing...", request.getRequestURI());
        return employeeManagementService.editEmployee(id, editEmployeeDto);
    }

    @DeleteMapping(value = ApiConstant.ApiPath.DELETE_ALL_X)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<DeletedEmployeeDto> deleteEmployeeList(@RequestBody List<Long> deleteEmployeeIdList,
                                                       HttpServletRequest request) {
        log.info("Request: {} is processing...", request.getRequestURI());
        return employeeManagementService.deleteEmployeeList(deleteEmployeeIdList);
    }

    @DeleteMapping(value = ApiConstant.ApiPath.DELETE_X)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DeletedEmployeeDto deleteEmployee(
            @PathVariable(name = EntityConstant.Column.ID_FIELD) Long id,
            HttpServletRequest request) {
        log.info("Request: {} is processing...", request.getRequestURI());
        return employeeManagementService.deleteEmployee(id);
    }

    @GetMapping(value = ApiConstant.ApiPath.ALL_X)
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> getAll(HttpServletRequest request) {
        log.info("Request: {} is processing...", request.getRequestURI());
        return employeeManagementService.getAllEmployees();
    }

    @GetMapping(value = ApiConstant.ApiPath.GET_X_BY_ID)
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto getEmployeeById(
            @PathVariable(name = EntityConstant.Column.ID_FIELD)
            @Min(value = 0, message = EntityConstant.Validation.General.ID_MUST_GREATER_THAN_ZERO_CONSTRAINT_MESSAGE) Long id,
            HttpServletRequest request) {
        log.info("Request: {} is processing...", request.getRequestURI());
        return employeeManagementService.getEmployeeById(id);
    }

    @GetMapping(value = ApiConstant.ApiPath.GET_X_BY_USERNAME)
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto getEmployeeByUserUsername(@PathVariable(name = EntityConstant.Column.USERNAME_FIELD) String username,
                                                 HttpServletRequest request) {
        log.info("Request: {} is processing...", request.getRequestURI());
        return employeeManagementService.getEmployeeByUserUsername(username);
    }
}
