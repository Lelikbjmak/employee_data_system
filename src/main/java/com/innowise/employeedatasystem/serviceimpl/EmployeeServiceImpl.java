package com.innowise.employeedatasystem.serviceimpl;

import com.innowise.employeedatasystem.entity.Employee;
import com.innowise.employeedatasystem.exception.EmployeeIsNotFoundException;
import com.innowise.employeedatasystem.repo.EmployeeRepository;
import com.innowise.employeedatasystem.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private static final String EMPLOYEE_IS_NOT_FOUND_BY_ID_EXCEPTION_MESSAGE = "Employee is not found.";

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee findEmployeeByUserUsername(String username) {
        // TODO: extract employee from user
        return null;
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public void deleteEmployees(List<Employee> deleteEmployeeList) throws EmployeeIsNotFoundException {
        employeeRepository.deleteAll(deleteEmployeeList);
    }

    @Override
    public Employee getEmployeeById(Long id) throws EmployeeIsNotFoundException {
        return employeeRepository.findById(id).orElseThrow(() ->
                new EmployeeIsNotFoundException(EMPLOYEE_IS_NOT_FOUND_BY_ID_EXCEPTION_MESSAGE,
                        Instant.now(), Map.of("Id", id)));
    }

    @Override
    public List<Employee> editEmployees(List<Employee> editEmployeeList) {
        return employeeRepository.saveAll(editEmployeeList);
    }

    @Override
    public Employee findByFirstLastMiddleNameAndHireDate(String firstName, String lastName, String middleName, Date hireDate) {
        return employeeRepository.findByFirstNameAndLastNameAndMiddleNameAndHireDate(firstName, lastName, middleName, hireDate).orElseThrow(() ->
                new EmployeeIsNotFoundException(EMPLOYEE_IS_NOT_FOUND_BY_ID_EXCEPTION_MESSAGE,
                        Instant.now(), Map.of(
                        "firstName", firstName,
                        "lastName", lastName,
                        "middleName", middleName,
                        "hireDate", hireDate
                )));
    }

}
