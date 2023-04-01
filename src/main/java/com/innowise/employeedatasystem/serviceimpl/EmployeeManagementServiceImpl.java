package com.innowise.employeedatasystem.serviceimpl;

import com.innowise.employeedatasystem.dto.*;
import com.innowise.employeedatasystem.entity.Employee;
import com.innowise.employeedatasystem.entity.User;
import com.innowise.employeedatasystem.mapper.EmployeeListMapper;
import com.innowise.employeedatasystem.mapper.EmployeeMapper;
import com.innowise.employeedatasystem.mapper.UserMapper;
import com.innowise.employeedatasystem.provider.RegistrationResponseProvider;
import com.innowise.employeedatasystem.service.EmployeeManagementService;
import com.innowise.employeedatasystem.util.ApiConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class EmployeeManagementServiceImpl implements EmployeeManagementService {

    private final UserServiceImpl userService;

    private final EmployeeServiceImpl employeeService;

    private final EmployeeMapper employeeMapper;

    private final EmployeeListMapper employeeListMapper;

    private final UserMapper userMapper;

    private final RegistrationResponseProvider responseProvider;

    @Override
    @PreAuthorize(ApiConstant.Security.HAS_ROLE_ADMIN)
    public RegistrationResponseDto registerEmployeeList(List<RegistrationDto> registrationRequestDto) {

        log.info("Register List<Employee>");

        List<EmployeeDto> registrationDtoList = registrationRequestDto.stream()
                .map(this::registerNewUserEmployee).toList();

        log.info("Successfully register List<Employee>");

        return responseProvider.generateRegistrationResponse(registrationDtoList);
    }

    @Override
    @PreAuthorize(ApiConstant.Security.HAS_ROLE_ADMIN)
    public RegistrationResponseDto registerEmployee(RegistrationDto registrationDto) {

        log.info("Register Employee");

        EmployeeDto registeredEmployee = registerNewUserEmployee(registrationDto);

        log.info("Successfully register Employee");

        return responseProvider.generateRegistrationResponse(List.of(registeredEmployee));
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {

        log.info("Get all employees");

        List<Employee> foundEmployeeList = employeeService.getAllEmployees();

        log.info("Successfully get all employees");

        return employeeListMapper.mapToDto(foundEmployeeList);
    }

    @Override
    @PreAuthorize(ApiConstant.Security.HAS_ROLE_ADMIN)
    public List<UpdatedEmployeeDto> editEmployeeList(List<EditEmployeeDto> editEmployeeDtoList) {

        log.info("Editing List<Employee>");

        List<Employee> editedEmployeeList = editEmployeeDtoList.stream()
                .map(editEmployeeDto -> {
                    Employee employeeToEdit = employeeService.getEmployeeById(editEmployeeDto.id());
                    updateEmployeeAccordingToEmployeeDto(employeeToEdit, editEmployeeDto);
                    return employeeToEdit;
                })
                .toList();
        employeeService.saveEmployeeList(editedEmployeeList);

        log.info("Successfully edit List<Employee>");

        return employeeListMapper.mapToUpdatedDtoList(editedEmployeeList);
    }

    @Override
    @PreAuthorize(ApiConstant.Security.HAS_ROLE_ADMIN)
    public UpdatedEmployeeDto editEmployee(Long employeeToEditId, EmployeeDto editedEmployeeDto) {

        log.info("Edit Employee");

        Employee employeeToEdit = employeeService.getEmployeeById(employeeToEditId);
        updateEmployeeAccordingToEmployeeDto(employeeToEdit, editedEmployeeDto);
        Employee savedEmployee = employeeService.saveEmployee(employeeToEdit);

        log.info("Successfully edit Employee");

        return employeeMapper.mapToUpdatedEmployeeDto(savedEmployee);
    }

    private void updateEmployeeAccordingToEmployeeDto(Employee employeeToEdit, EmployeeDto editedEmployeeDto) {

        if (editedEmployeeDto.hireDate() != null)
            employeeToEdit.setHireDate(editedEmployeeDto.hireDate());

        if (editedEmployeeDto.firstName() != null)
            employeeToEdit.setFirstName(editedEmployeeDto.firstName());

        if (editedEmployeeDto.lastName() != null)
            employeeToEdit.setLastName(editedEmployeeDto.lastName());

        if (editedEmployeeDto.middleName() != null)
            employeeToEdit.setMiddleName(editedEmployeeDto.middleName());
    }

    private void updateEmployeeAccordingToEmployeeDto(Employee employeeToEdit, EditEmployeeDto editedEmployeeDto) {

        if (editedEmployeeDto.hireDate() != null)
            employeeToEdit.setHireDate(editedEmployeeDto.hireDate());

        if (editedEmployeeDto.firstName() != null)
            employeeToEdit.setFirstName(editedEmployeeDto.firstName());

        if (editedEmployeeDto.lastName() != null)
            employeeToEdit.setLastName(editedEmployeeDto.lastName());

        if (editedEmployeeDto.middleName() != null)
            employeeToEdit.setMiddleName(editedEmployeeDto.middleName());
    }

    @Override
    @PreAuthorize(ApiConstant.Security.HAS_ROLE_ADMIN)
    public List<DeletedEmployeeDto> deleteEmployeeList(List<Long> deleteEmployeeIdList) {

        log.info("Delete List<Employee> with idList: {}", deleteEmployeeIdList);

        List<Employee> deleteEmployeeList = employeeService.getEmployeeListByIdList(deleteEmployeeIdList);
        employeeService.deleteEmployeeList(deleteEmployeeList);

        log.info("Successfully deleted List<Employee>. Deleted IdList {}", deleteEmployeeIdList);

        return employeeListMapper.mapToDeletedDtoList(deleteEmployeeList);
    }

    @Override
    @PreAuthorize(ApiConstant.Security.HAS_ROLE_ADMIN)
    public DeletedEmployeeDto deleteEmployee(Long id) {

        log.info("Delete Employee by id. id: {}", id);

        Employee deletedEmployee = employeeService.getEmployeeById(id);
        employeeService.deleteEmployee(deletedEmployee);

        log.info("Successfully deleted Employee with id: {}", id);

        return employeeMapper.mapToDeletedEmployeeDto(deletedEmployee);
    }

    @Override
    public List<EmployeeDto> getEmployeeListByUserUsernameList(List<String> usernameList) {

        log.info("Find List<Employee> by usernameList: {}", usernameList);

        List<Employee> foundEmployeeList = employeeService.findEmployeeListByUserUsernameList(usernameList);

        log.info("Successfully found List<Employee> by usernameList: {}", usernameList);

        return employeeListMapper.mapToDto(foundEmployeeList);
    }

    @Override
    public EmployeeDto getEmployeeByUserUsername(String username) {

        log.info("Find Employee by associated user; username: {}", username);

        Employee foundEmployee = employeeService.findEmployeeByUserUsername(username);

        log.info("Successfully found Employee by associated user; username: {}", username);

        return employeeMapper.mapToDto(foundEmployee);
    }

    @Override
    public List<EmployeeDto> getEmployeeListByIdList(List<Long> idList) {

        log.info("Find List<Employee> by idList: {}", idList);

        List<Employee> foundEmployeeList = employeeService.getEmployeeListByIdList(idList);

        log.info("Successfully found List<Employee> by idList: {}", idList);

        return employeeListMapper.mapToDto(foundEmployeeList);
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {

        log.info("Find Employee by id: {}", id);

        Employee foundEmployee = employeeService.getEmployeeById(id);

        log.info("Find Employee by id: {}", id);

        return employeeMapper.mapToDto(foundEmployee);
    }

    private EmployeeDto registerNewUserEmployee(RegistrationDto registrationDto) {

        User user = userMapper.mapToEntity(registrationDto.userDto());
        Employee employee = employeeMapper.mapToEntity(registrationDto.employeeDto());

        user.setEmployee(employee);
        employee.setUser(user);

        userService.registerUser(user);
        Employee registeredEmployee = employeeService.saveEmployee(employee);

        return employeeMapper.mapToDto(registeredEmployee);
    }
}
