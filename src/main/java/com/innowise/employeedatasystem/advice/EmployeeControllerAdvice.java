package com.innowise.employeedatasystem.advice;

import com.innowise.employeedatasystem.controller.EmployeeController;
import com.innowise.employeedatasystem.dto.ExceptionResponseDto;
import com.innowise.employeedatasystem.exception.EmployeeIsNotFoundException;
import com.innowise.employeedatasystem.exception.RoleIsNotFoundException;
import com.innowise.employeedatasystem.exception.UserIsNotFoundException;
import com.innowise.employeedatasystem.provider.ExceptionResponseProvider;
import com.innowise.employeedatasystem.provider.ResponseEntityProvider;
import com.innowise.employeedatasystem.util.ApiConstant;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice(assignableTypes = EmployeeController.class)
public class EmployeeControllerAdvice {

    private final ResponseEntityProvider<ExceptionResponseDto> responseEntityProvider;

    private final ExceptionResponseProvider exceptionResponseProvider;

    @ExceptionHandler(EmployeeIsNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleEmployeeNotFound(
            EmployeeIsNotFoundException exception, HttpServletRequest request) {

        log.error("Resolve exception: {} for URI {}. Message: \t{}",
                exception.getClass().getName(), request.getRequestURI(), exception.getMessage());

        HttpStatus status = HttpStatus.NOT_FOUND;

        ExceptionResponseDto responseDto = exceptionResponseProvider.getExceptionResponse(
                status, exception.getMessage(), exception.getAdditional());

        return responseEntityProvider.generateResponseEntity(responseDto, status);
    }

    @ExceptionHandler(RoleIsNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleRoleNotFound(
            RoleIsNotFoundException exception, HttpServletRequest request) {

        log.error("Resolve exception: {} for URI {}. Message: \t{}",
                exception.getClass().getName(), request.getRequestURI(), exception.getMessage());

        HttpStatus status = HttpStatus.NOT_FOUND;

        ExceptionResponseDto responseDto = exceptionResponseProvider.getExceptionResponse(
                status, exception.getMessage(), exception.getAdditional());

        return responseEntityProvider.generateResponseEntity(responseDto, status);
    }

    @ExceptionHandler(UserIsNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleUsernameNotFound(
            UserIsNotFoundException exception, HttpServletRequest request) {

        log.error("Resolve exception: {} for URI {}. Message: \t{}",
                exception.getClass().getName(), request.getRequestURI(), exception.getMessage());

        HttpStatus status = HttpStatus.NOT_FOUND;

        ExceptionResponseDto responseDto = exceptionResponseProvider.getExceptionResponse(
                status, exception.getMessage(), exception.getAdditional());

        return responseEntityProvider.generateResponseEntity(responseDto, status);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponseDto> handleNullEntity(
            HttpMessageNotReadableException exception, HttpServletRequest request) {

        log.error("Resolve exception: {} for URI {}. Message: \t{}",
                exception.getClass().getName(), request.getRequestURI(), exception.getMessage());

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ExceptionResponseDto responseDto = exceptionResponseProvider.getExceptionResponse(
                status, ApiConstant.Validation.REQUEST_BODY_MISSED_MESSAGE, null);

        return responseEntityProvider.generateResponseEntity(responseDto, status);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionResponseDto> handleTypeMismatch(
            MethodArgumentTypeMismatchException exception, HttpServletRequest request) {

        log.error("Resolve exception: {} for URI {}. Message: \t{}",
                exception.getClass().getName(), request.getRequestURI(), exception.getMessage());

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ExceptionResponseDto responseDto = exceptionResponseProvider.getExceptionResponse(
                status, exception.getMessage(), Map.of(
                        exception.getName(), exception.getRequiredType().getName())
        );

        return responseEntityProvider.generateResponseEntity(responseDto, status);
    }
}
