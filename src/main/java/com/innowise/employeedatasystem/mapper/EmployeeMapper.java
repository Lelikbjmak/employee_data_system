package com.innowise.employeedatasystem.mapper;

import com.innowise.employeedatasystem.dto.DeletedEmployeeDto;
import com.innowise.employeedatasystem.dto.EmployeeDto;
import com.innowise.employeedatasystem.dto.RegistrationEmployeeDto;
import com.innowise.employeedatasystem.dto.UpdatedEmployeeDto;
import com.innowise.employeedatasystem.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = UserMapper.class)
public interface EmployeeMapper {

    @Mapping(source = "userDto", target = "user")
    Employee mapToEntity(EmployeeDto employeeDto);

    Employee mapToEntity(RegistrationEmployeeDto employeeDto);

    @Mapping(source = "user", target = "userDto")
    EmployeeDto mapToDto(Employee employee);

    @Mapping(target = "isUpdated", expression = "java(true)")
    UpdatedEmployeeDto mapToUpdatedEmployeeDto(Employee employee);

    @Mapping(target = "isDeleted", expression = "java(true)")
    DeletedEmployeeDto mapToDeletedEmployeeDto(Employee employee);
}
