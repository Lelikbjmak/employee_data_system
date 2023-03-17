package com.innowise.employeedatasystem.controller;

import com.innowise.employeedatasystem.dto.EmployeeDto;
import com.innowise.employeedatasystem.dto.RegistrationDto;
import com.innowise.employeedatasystem.dto.RegistrationResponseDto;
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
    @ResponseStatus(HttpStatus.ACCEPTED)
    public RegistrationResponseDto addEmployee(@RequestBody List<RegistrationDto> registrationDto) {
        return employeeManagementService.registerEmployees(registrationDto);
    }

    @GetMapping("all")
    public List<EmployeeDto> getAll() {
        return employeeManagementService.getAllEmployees();
    }


}
