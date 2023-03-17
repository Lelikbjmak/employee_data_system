package com.innowise.employeedatasystem.serviceimpl;

import com.innowise.employeedatasystem.dto.EmployeeDto;
import com.innowise.employeedatasystem.dto.RegistrationDto;
import com.innowise.employeedatasystem.dto.RegistrationResponseDto;
import com.innowise.employeedatasystem.entity.Employee;
import com.innowise.employeedatasystem.entity.User;
import com.innowise.employeedatasystem.mapper.EmployeeMapper;
import com.innowise.employeedatasystem.mapper.UserMapper;
import com.innowise.employeedatasystem.service.EmployeeManagementService;
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

        List<RegistrationDto> registrationDtoList = registrationRequestDto.stream()
                .map(this::registerNewUserEmployee).toList();

        HttpStatus status = HttpStatus.ACCEPTED;

        return RegistrationResponseDto.builder()
                .timestamp(new Date())
                .code(status.value())
                .status(status.name())
                .message("Successfully added employees.")
                .content(registrationDtoList)
                .build();
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees().stream()
                .map(employeeMapper::toEmployeeDto).toList();
    }

    private RegistrationDto registerNewUserEmployee(RegistrationDto registrationDto) {

        User user = userMapper.toUserEntity(registrationDto.getUserDto());
        Employee employee = employeeMapper.toEmployeeEntity(registrationDto.getEmployeeDto());

        user.setEmployee(employee);
        employee.setUser(user);

        userService.registerUser(user);
        employeeService.save(employee);

        return registrationDto;
    }


}
