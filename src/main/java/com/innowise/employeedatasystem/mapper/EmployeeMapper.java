package com.innowise.employeedatasystem.mapper;

import com.innowise.employeedatasystem.dto.EmployeeDto;
import com.innowise.employeedatasystem.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public Employee toEmployeeEntity(EmployeeDto registrationEmployeeDto) {
        return Employee.builder()
                .firstName(registrationEmployeeDto.getFirstName())
                .middleName(registrationEmployeeDto.getMiddleName())
                .lastName(registrationEmployeeDto.getLastName())
                .hireDate(registrationEmployeeDto.getHireDate())
                .build();
    }

    public EmployeeDto toEmployeeDto(Employee employeeEntity) {
        return EmployeeDto.builder()
                .firstName(employeeEntity.getFirstName())
                .lastName(employeeEntity.getLastName())
                .middleName(employeeEntity.getMiddleName())
                .hireDate(employeeEntity.getHireDate())
                .build();
    }

}
