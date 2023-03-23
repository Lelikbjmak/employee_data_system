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
    public RegistrationResponseDto registerEmployeeList(List<RegistrationDto> registrationRequestDto) {

        List<EmployeeDto> registrationDtoList = registrationRequestDto.stream()
                .map(this::registerNewUserEmployee).toList();

        return generateRegistrationResponse(registrationDtoList);
    }

    @Override
    public RegistrationResponseDto registerEmployee(RegistrationDto registrationDto) {

        EmployeeDto registeredEmployee = registerNewUserEmployee(registrationDto);

        return generateRegistrationResponse(List.of(registeredEmployee));
    }

    private RegistrationResponseDto generateRegistrationResponse(List<EmployeeDto> registeredEmployeeList) {
        return RegistrationResponseDto.builder()
                .timestamp(new Date())
                .code(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED.name())
                .message(GeneralConstant.Message.EMPLOYEE_SUCCESSFULLY_ADDED_MESSAGE)
                .content(registeredEmployeeList)
                .build();
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees().stream()
                .map(employeeMapper::toEmployeeDto).toList();
    }

    @Override
    public List<UpdatedEmployeeDto> editEmployeeList(List<EmployeeDto> editEmployeeDtoList) {

        List<Employee> editedEmployeeList = editEmployeeDtoList.stream()
                .map(employeeDto -> {
                    Employee employeeToEdit = employeeService.getEmployeeById(employeeDto.getId());
                    return updateEmployeeAccordingToEmployeeDto(employeeToEdit, employeeDto);
                }).toList();
        employeeService.saveEmployeeList(editedEmployeeList);
        return editedEmployeeList.stream()
                .map(employeeMapper::toUpdatedEmployeeDto)
                .toList();
    }

    @Override
    public UpdatedEmployeeDto editEmployee(Long employeeToEditId, EmployeeDto editedEmployeeDto) {
        Employee employeeToEdit = employeeService.getEmployeeById(employeeToEditId);
        updateEmployeeAccordingToEmployeeDto(employeeToEdit, editedEmployeeDto);
        Employee savedEmployee = employeeService.saveEmployee(employeeToEdit);
        return employeeMapper.toUpdatedEmployeeDto(savedEmployee);
    }

    private Employee updateEmployeeAccordingToEmployeeDto(Employee employeeToEdit, EmployeeDto editedEmployeeDto) {

        if (editedEmployeeDto.getHireDate() != null)
            employeeToEdit.setHireDate(editedEmployeeDto.getHireDate());

        if (editedEmployeeDto.getFirstName() != null)
            employeeToEdit.setFirstName(editedEmployeeDto.getFirstName());

        if (editedEmployeeDto.getLastName() != null)
            employeeToEdit.setLastName(editedEmployeeDto.getLastName());

        if (editedEmployeeDto.getMiddleName() != null)
            employeeToEdit.setMiddleName(editedEmployeeDto.getMiddleName());

        return employeeToEdit;
    }

    @Override
    public List<DeletedEmployeeDto> deleteEmployeeList(List<Long> deleteEmployeeIdList) {
        List<Employee> deleteEmployeeList = employeeService.getEmployeeListByIdList(deleteEmployeeIdList);
        employeeService.deleteEmployeeList(deleteEmployeeList);
        return deleteEmployeeList.stream().map(employeeMapper::toDeletedEmployeeDto).toList();
    }

    @Override
    public DeletedEmployeeDto deleteEmployee(Long id) {
        Employee deletedEmployee = employeeService.getEmployeeById(id);
        employeeService.deleteEmployee(deletedEmployee);
        return employeeMapper.toDeletedEmployeeDto(deletedEmployee);
    }

    @Override
    public List<EmployeeDto> getEmployeeListByUserUsernameList(List<String> usernameList) {
        return employeeService.findEmployeeListByUserUsernameList(usernameList).stream()
                .map(employeeMapper::toEmployeeDto).toList();
    }

    @Override
    public EmployeeDto getEmployeeByUserUsername(String username) {
        return employeeMapper.toEmployeeDto(employeeService.findEmployeeByUserUsername(username));
    }

    @Override
    public List<EmployeeDto> getEmployeeListByIdList(List<Long> idList) {
        return employeeService.getEmployeeListByIdList(idList).stream()
                .map(employeeMapper::toEmployeeDto).toList();
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
        Employee registeredEmployee = employeeService.saveEmployee(employee);

        return employeeMapper.toEmployeeDto(registeredEmployee);
    }

}
