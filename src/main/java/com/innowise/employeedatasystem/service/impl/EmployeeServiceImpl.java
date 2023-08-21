package com.innowise.employeedatasystem.service.impl;

import com.innowise.employeedatasystem.entity.Employee;
import com.innowise.employeedatasystem.entity.RoleEnum;
import com.innowise.employeedatasystem.exception.EmployeeIsNotFoundException;
import com.innowise.employeedatasystem.exception.NullEntityException;
import com.innowise.employeedatasystem.repo.EmployeeRepository;
import com.innowise.employeedatasystem.service.EmployeeService;
import com.innowise.employeedatasystem.util.EntityConstant;
import com.innowise.employeedatasystem.util.GeneralConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee findEmployeeByUserUsername(String username) {
        return employeeRepository.findByUser_username(username).orElseThrow(() ->
                new EmployeeIsNotFoundException(GeneralConstant.Message.EMPLOYEE_IS_NOT_FOUND_BY_ID_EXCEPTION_MESSAGE,
                        Instant.now(), Map.of(EntityConstant.Column.USERNAME_FIELD, username)));
    }

    @Override
    public List<Employee> findEmployeeListByUserUsernameList(List<String> usernameList) {
        return employeeRepository.findAllByUser_usernameIn(usernameList);
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        if (employee == null)
            throw new NullEntityException(GeneralConstant.Message.NULL_ENTITY_EXCEPTION_MESSAGE,
                    Thread.currentThread().getStackTrace()[1].getMethodName(), RoleEnum.class.getName());
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public void deleteEmployeeList(List<Employee> deleteEmployeeList) {
        employeeRepository.deleteAll(deleteEmployeeList);
    }

    @Override
    public void deleteEmployee(Employee employee) {
        employeeRepository.delete(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) throws EmployeeIsNotFoundException {
        return employeeRepository.findById(id).orElseThrow(() ->
                new EmployeeIsNotFoundException(GeneralConstant.Message.EMPLOYEE_IS_NOT_FOUND_BY_ID_EXCEPTION_MESSAGE,
                        Instant.now(), Map.of(EntityConstant.Column.ID_FIELD, id)));

    }

    @Override
    public List<Employee> getEmployeeListByIdList(List<Long> employeeIdList) {
        return employeeRepository.findAllById(employeeIdList);
    }

    @Override
    public List<Employee> saveEmployeeList(List<Employee> editEmployeeList) {
        return employeeRepository.saveAll(editEmployeeList);
    }

    @Override
    public Employee findByFirstLastMiddleNameAndHireDate(String firstName, String lastName, String middleName, Date hireDate) {
        return employeeRepository.findByFirstNameAndLastNameAndMiddleNameAndHireDate(firstName, lastName, middleName, hireDate).orElseThrow(() ->
                new EmployeeIsNotFoundException(GeneralConstant.Message.EMPLOYEE_IS_NOT_FOUND_BY_ID_EXCEPTION_MESSAGE,
                        Instant.now(), Map.of(
                        EntityConstant.Column.EMPLOYEE_FIRST_NAME_FIELD, firstName,
                        EntityConstant.Column.EMPLOYEE_LAST_NAME_FIELD, lastName,
                        EntityConstant.Column.EMPLOYEE_MIDDLE_NAME_FIELD, middleName,
                        EntityConstant.Column.EMPLOYEE_HIRE_DATE_FIELD, hireDate
                )));
    }

}
