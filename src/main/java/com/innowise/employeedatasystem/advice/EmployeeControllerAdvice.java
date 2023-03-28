package com.innowise.employeedatasystem.advice;

import com.innowise.employeedatasystem.controller.EmployeeController;
import com.innowise.employeedatasystem.dto.ExceptionResponseDto;
import com.innowise.employeedatasystem.exception.EmployeeIsNotFoundException;
import com.innowise.employeedatasystem.exception.RoleIsNotFoundException;
import com.innowise.employeedatasystem.exception.UserIsNotFoundException;
import com.innowise.employeedatasystem.provider.ExceptionResponseProvider;
import com.innowise.employeedatasystem.provider.ResponseEntityProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = EmployeeController.class)
@RequiredArgsConstructor
public class EmployeeControllerAdvice {

    private final ResponseEntityProvider<ExceptionResponseDto> responseEntityProvider;

    private final ExceptionResponseProvider exceptionResponseProvider;

    @ExceptionHandler(EmployeeIsNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> employeeIsNotFoundExceptionHandling(EmployeeIsNotFoundException e) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        ExceptionResponseDto responseDto = exceptionResponseProvider.getExceptionResponse(
                status, e.getMessage(), e.getAdditional());

        return responseEntityProvider.generateResponseEntity(responseDto, status);
    }

    @ExceptionHandler(RoleIsNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> roleIsNotFoundExceptionHandling(RoleIsNotFoundException e) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        ExceptionResponseDto responseDto = exceptionResponseProvider.getExceptionResponse(
                status, e.getMessage(), e.getAdditional());

        return responseEntityProvider.generateResponseEntity(responseDto, status);
    }

    @ExceptionHandler(UserIsNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> usernameNotFoundExceptionHandling(UserIsNotFoundException e) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        ExceptionResponseDto responseDto = exceptionResponseProvider.getExceptionResponse(
                status, e.getMessage(), e.getAdditional());

        return responseEntityProvider.generateResponseEntity(responseDto, status);
    }
}
