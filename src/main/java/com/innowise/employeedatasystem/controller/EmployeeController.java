package com.innowise.employeedatasystem.controller;

import com.innowise.employeedatasystem.dto.*;
import com.innowise.employeedatasystem.exception.EmployeeIsNotFoundException;
import com.innowise.employeedatasystem.serviceimpl.EmployeeManagementServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/employee")
public class EmployeeController {

    private final EmployeeManagementServiceImpl employeeManagementService;

    @PostMapping("add")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationResponseDto addEmployee(@RequestBody List<RegistrationDto> registrationDto) {
        return employeeManagementService.registerEmployees(registrationDto);
    }

    @PostMapping("edit")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<UpdatedEmployeeDto> editEmployee(@RequestBody List<EmployeeDto> editEmployeeDtoList) {
        return employeeManagementService.editEmployees(editEmployeeDtoList);
    }

    @PostMapping("delete")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<DeletedEmployeeDto> deleteEmployees(@RequestBody List<EmployeeDto> deleteEmployeeDtoList) {
        return employeeManagementService.deleteEmployees(deleteEmployeeDtoList);
    }

    @GetMapping("all")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> getAll() {
        return employeeManagementService.getAllEmployees();
    }

    @GetMapping("get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto getEmployeeById(@PathVariable(name = "id") Long id) throws EmployeeIsNotFoundException {
        return employeeManagementService.getEmployeeById(id);
    }

    @GetMapping("get/by/user")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto getEmployeeByUserUsername(@RequestParam(name = "username") String username) {
        return employeeManagementService.getEmployeeByUserUsername(username);
    }

}
