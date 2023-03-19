package com.innowise.employeedatasystem.mapper;

import com.innowise.employeedatasystem.dto.DeletedEmployeeDto;
import com.innowise.employeedatasystem.dto.EmployeeDto;
import com.innowise.employeedatasystem.dto.RegistrationEmployeeDto;
import com.innowise.employeedatasystem.dto.UpdatedEmployeeDto;
import com.innowise.employeedatasystem.entity.Employee;
import com.innowise.employeedatasystem.serviceimpl.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeMapper {

    private static final Boolean IS_EMPLOYEE_DELETED = true;

    private static final Boolean IS_EMPLOYEE_UPDATED = true;

    private final EmployeeServiceImpl employeeService;

    private final UserMapper userMapper;

    public Employee toEmployeeEntity(RegistrationEmployeeDto registrationEmployeeDto) {
        return Employee.builder()
                .firstName(registrationEmployeeDto.getFirstName())
                .middleName(registrationEmployeeDto.getMiddleName())
                .lastName(registrationEmployeeDto.getLastName())
                .hireDate(registrationEmployeeDto.getHireDate())
                .build();
    }

    public EmployeeDto toEmployeeDto(Employee employeeEntity) {
        return EmployeeDto.builder()
                .id(employeeEntity.getId() != null ? employeeEntity.getId() : null)
                .firstName(employeeEntity.getFirstName())
                .lastName(employeeEntity.getLastName())
                .middleName(employeeEntity.getMiddleName())
                .hireDate(employeeEntity.getHireDate())
                .userDto(userMapper.toUserDto(employeeEntity.getUser()))
                .build();
    }

    public DeletedEmployeeDto toDeletedEmployeeDto(Employee employeeEntity) {
        return DeletedEmployeeDto.builder()
                .firstName(employeeEntity.getFirstName())
                .lastName(employeeEntity.getLastName())
                .middleName(employeeEntity.getMiddleName())
                .hireDate(employeeEntity.getHireDate())
                .isDeleted(IS_EMPLOYEE_DELETED)
                .build();
    }

    public Employee toEmployeeEntityFromDatabase(EmployeeDto employeeDto) {

        boolean isIdPresent = employeeDto.getId() != null;
        Employee employee;

        if (isIdPresent) {
            employee = employeeService.getEmployeeById(employeeDto.getId());

        } else if (employeeDto.getUserDto() == null) {
            employee = employeeService.findByFirstLastMiddleNameAndHireDate(employeeDto.getFirstName(), employeeDto.getLastName(),
                    employeeDto.getMiddleName(), employeeDto.getHireDate());

        } else {
            employee = employeeService.findEmployeeByUserUsername(employeeDto.getUserDto().getUsername());
        }

        return employee;
    }

    public UpdatedEmployeeDto toUpdatedEmployeeDto(Employee employee) {
        return UpdatedEmployeeDto.builder()
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .middleName(employee.getMiddleName())
                .hireDate(employee.getHireDate())
                .isUpdated(IS_EMPLOYEE_UPDATED)
                .build();
    }
}
