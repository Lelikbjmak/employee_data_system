package com.innowise.employeedatasystem.mapper;

import com.innowise.employeedatasystem.dto.DeletedEmployeeDto;
import com.innowise.employeedatasystem.dto.EmployeeDto;
import com.innowise.employeedatasystem.dto.UpdatedEmployeeDto;
import com.innowise.employeedatasystem.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = EmployeeMapper.class)
public interface EmployeeListMapper {

    List<EmployeeDto> mapToDto(List<Employee> employeeList);

    List<UpdatedEmployeeDto> mapToUpdatedDtoList(List<Employee> employeeList);

    List<DeletedEmployeeDto> mapToDeletedDtoList(List<Employee> employeeList);
}
