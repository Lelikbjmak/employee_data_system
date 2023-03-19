package com.innowise.employeedatasystem.advice;

import com.innowise.employeedatasystem.controller.EmployeeController;
import com.innowise.employeedatasystem.dto.ExceptionResponseDto;
import com.innowise.employeedatasystem.exception.EmployeeIsNotFoundException;
import com.innowise.employeedatasystem.exception.RoleIsNotFoundException;
import com.innowise.employeedatasystem.exception.UserIsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(assignableTypes = EmployeeController.class)
public class EmployeeControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmployeeIsNotFoundException.class)
    public ResponseEntity<Object> employeeIsNotFoundExceptionHandling(EmployeeIsNotFoundException e) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        ExceptionResponseDto responseDto = ExceptionResponseDto.builder()
                .timestamp(e.getTimestamp())
                .status(status.name())
                .code(status.value())
                .message(e.getMessage())
                .additional(e.getAdditional())
                .build();

        return buildResponseEntity(responseDto, status);
    }

    @ExceptionHandler(RoleIsNotFoundException.class)
    public ResponseEntity<Object> roleIsNotFoundExceptionHandling(RoleIsNotFoundException e) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        ExceptionResponseDto responseDto = ExceptionResponseDto.builder()
                .timestamp(e.getTimestamp())
                .status(status.name())
                .code(status.value())
                .message(e.getMessage())
                .additional(e.getAdditional())
                .build();

        return buildResponseEntity(responseDto, status);
    }

    @ExceptionHandler(UserIsNotFoundException.class)
    public ResponseEntity<Object> usernameNotFoundExceptionHandling(UserIsNotFoundException e) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        ExceptionResponseDto responseDto = ExceptionResponseDto.builder()
                .timestamp(e.getTimestamp())
                .status(status.name())
                .code(status.value())
                .message(e.getMessage())
                .additional(e.getAdditional())
                .build();

        return buildResponseEntity(responseDto, status);
    }


    private static ResponseEntity<Object> buildResponseEntity(ExceptionResponseDto exceptionResponseDto,
                                                              HttpStatus status) {
        return new ResponseEntity<>(exceptionResponseDto, status);
    }
}
