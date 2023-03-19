package com.innowise.employeedatasystem.service;

import com.innowise.employeedatasystem.entity.Employee;
import com.innowise.employeedatasystem.exception.EmployeeIsNotFoundException;

import java.util.Date;
import java.util.List;

public interface EmployeeService {

    Employee findEmployeeByUserUsername(String username);

    Employee save(Employee employee);

    List<Employee> getAllEmployees();

    void deleteEmployees(List<Employee> deleteEmployeeList);

    Employee getEmployeeById(Long id) throws EmployeeIsNotFoundException;

    List<Employee> editEmployees(List<Employee> editEmployeeList);

    Employee findByFirstLastMiddleNameAndHireDate(String firstName, String lastName, String middleName, Date hireDate);
}
