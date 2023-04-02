package com.innowise.employeedatasystem.mapper;

import com.innowise.employeedatasystem.dto.DeletedEmployeeDto;
import com.innowise.employeedatasystem.dto.EmployeeDto;
import com.innowise.employeedatasystem.dto.RegistrationEmployeeDto;
import com.innowise.employeedatasystem.dto.UpdatedEmployeeDto;
import com.innowise.employeedatasystem.entity.Employee;
import com.innowise.employeedatasystem.util.DtoConstant;
import com.innowise.employeedatasystem.util.MapStructConstant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = UserMapper.class)
public interface EmployeeMapper {

    @Mapping(source = DtoConstant.Json.JSON_USER_DTO_NAME, target = DtoConstant.Json.JSON_USER_NAME)
    Employee mapToEntity(EmployeeDto employeeDto);

    Employee mapToEntity(RegistrationEmployeeDto employeeDto);

    @Mapping(source = DtoConstant.Json.JSON_USER_NAME, target = DtoConstant.Json.JSON_USER_DTO_NAME)
    EmployeeDto mapToDto(Employee employee);

    @Mapping(target = DtoConstant.Json.IS_UPDATED_FIELD_NAME, expression = MapStructConstant.SET_IS_UPDATED_OR_DELETED_FIELD_EXPRESSION)
    UpdatedEmployeeDto mapToUpdatedEmployeeDto(Employee employee);

    @Mapping(target = DtoConstant.Json.IS_DELETED_FIELD_NAME, expression = MapStructConstant.SET_IS_UPDATED_OR_DELETED_FIELD_EXPRESSION)
    DeletedEmployeeDto mapToDeletedEmployeeDto(Employee employee);
}
