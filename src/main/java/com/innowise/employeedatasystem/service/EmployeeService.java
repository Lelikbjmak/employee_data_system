package com.innowise.employeedatasystem.service;

import com.innowise.employeedatasystem.entity.Employee;

import java.util.Date;
import java.util.List;

public interface EmployeeService {

    Employee findEmployeeByUserUsername(String username);

    List<Employee> findEmployeeListByUserUsernameList(List<String> usernameList);

    Employee saveEmployee(Employee employee);

    List<Employee> saveEmployeeList(List<Employee> editEmployeeList);

    List<Employee> getAllEmployees();

    void deleteEmployeeList(List<Employee> deleteEmployeeList);

    void deleteEmployee(Employee employee);

    Employee getEmployeeById(Long id);

    List<Employee> getEmployeeListByIdList(List<Long> employeeIdList);

    Employee findByFirstLastMiddleNameAndHireDate(String firstName, String lastName, String middleName, Date hireDate);
}
