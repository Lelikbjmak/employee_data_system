package com.innowise.employeedatasystem.service;

import com.innowise.employeedatasystem.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Optional<Employee> findEmployeeByUserUsername(String username);

    Employee save(Employee employee);

    List<Employee> getAllEmployees();
}
