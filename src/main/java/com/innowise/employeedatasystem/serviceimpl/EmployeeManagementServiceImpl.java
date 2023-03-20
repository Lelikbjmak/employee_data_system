package com.innowise.employeedatasystem.serviceimpl;

import com.innowise.employeedatasystem.dto.*;
import com.innowise.employeedatasystem.entity.Employee;
import com.innowise.employeedatasystem.entity.User;
import com.innowise.employeedatasystem.mapper.EmployeeMapper;
import com.innowise.employeedatasystem.mapper.UserMapper;
import com.innowise.employeedatasystem.service.EmployeeManagementService;
import com.innowise.employeedatasystem.util.GeneralConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
public class EmployeeManagementServiceImpl implements EmployeeManagementService {

    private final UserServiceImpl userService;

    private final EmployeeServiceImpl employeeService;

    private final EmployeeMapper employeeMapper;

    private final UserMapper userMapper;

    @Override
    public RegistrationResponseDto registerEmployees(List<RegistrationDto> registrationRequestDto) {

        List<EmployeeDto> registrationDtoList = registrationRequestDto.stream()
                .map(this::registerNewUserEmployee).toList();

        HttpStatus status = HttpStatus.CREATED;

        return RegistrationResponseDto.builder()
                .timestamp(new Date())
                .code(status.value())
                .status(status.name())
                .message(GeneralConstant.Message.EMPLOYEE_SUCCESSFULLY_ADDED_MESSAGE)
                .content(registrationDtoList)
                .build();
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees().stream()
                .map(employeeMapper::toEmployeeDto).toList();
    }

    @Override
    public List<UpdatedEmployeeDto> editEmployees(List<EmployeeDto> editEmployeeDtoList) {
        List<Employee> updatedEmployees = editEmployeeDtoList.stream()
                .map(this::updateEmployeeAccordingToEmployeeDto).toList();

        updatedEmployees = employeeService.editEmployees(updatedEmployees);

        return updatedEmployees.stream()
                .map(employeeMapper::toUpdatedEmployeeDto)
                .toList();
    }

    private Employee updateEmployeeAccordingToEmployeeDto(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEmployeeEntityFromDatabase(employeeDto);

        if (employeeDto.getHireDate() != null)
            employee.setHireDate(employeeDto.getHireDate());

        if (employeeDto.getFirstName() != null)
            employee.setFirstName(employeeDto.getFirstName());

        if (employeeDto.getLastName() != null)
            employee.setLastName(employeeDto.getLastName());

        if (employeeDto.getMiddleName() != null)
            employee.setMiddleName(employeeDto.getMiddleName());

        return employee;
    }

    @Override
    public List<DeletedEmployeeDto> deleteEmployees(List<EmployeeDto> deleteEmployeeDtoList) {

        List<Employee> deleteEmployeeList = deleteEmployeeDtoList.stream()
                .map(employeeMapper::toEmployeeEntityFromDatabase).toList();

        employeeService.deleteEmployees(deleteEmployeeList);

        return deleteEmployeeList.stream().map(employeeMapper::toDeletedEmployeeDto).toList();
    }

    @Override
    public EmployeeDto getEmployeeByUserUsername(String username) {
        return employeeMapper.toEmployeeDto(employeeService.findEmployeeByUserUsername(username));
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        return employeeMapper.toEmployeeDto(employeeService.getEmployeeById(id));
    }

    private EmployeeDto registerNewUserEmployee(RegistrationDto registrationDto) {

        User user = userMapper.toUserEntity(registrationDto.getUserDto());
        Employee employee = employeeMapper.toEmployeeEntity(registrationDto.getEmployeeDto());

        user.setEmployee(employee);
        employee.setUser(user);

        userService.registerUser(user);
        Employee registeredEmployee = employeeService.save(employee);

        return employeeMapper.toEmployeeDto(registeredEmployee);
    }


}
