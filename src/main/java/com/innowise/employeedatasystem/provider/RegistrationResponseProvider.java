package com.innowise.employeedatasystem.provider;

import com.innowise.employeedatasystem.dto.EmployeeDto;
import com.innowise.employeedatasystem.dto.RegistrationResponseDto;
import com.innowise.employeedatasystem.util.GeneralConstant;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class RegistrationResponseProvider {

    public RegistrationResponseDto generateRegistrationResponse(List<EmployeeDto> content) {
        return RegistrationResponseDto.builder()
                .timestamp(new Date())
                .status(HttpStatus.CREATED.name())
                .code(HttpStatus.CREATED.value())
                .message(GeneralConstant.Message.EMPLOYEE_SUCCESSFULLY_ADDED_MESSAGE)
                .additional(content)
                .build();
    }
}
