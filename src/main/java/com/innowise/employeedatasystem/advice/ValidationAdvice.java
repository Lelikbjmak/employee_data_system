package com.innowise.employeedatasystem.advice;

import com.innowise.employeedatasystem.dto.ExceptionResponseDto;
import com.innowise.employeedatasystem.provider.ExceptionResponseProvider;
import com.innowise.employeedatasystem.provider.ResponseEntityProvider;
import com.innowise.employeedatasystem.util.GeneralConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ValidationAdvice {

    private final ExceptionResponseProvider responseProvider;

    private final ResponseEntityProvider<ExceptionResponseDto> responseEntityProvider;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDto> notValidDerivedData(
            MethodArgumentNotValidException exception, HttpServletRequest request) {

        log.error("Resolve exception: {} for URI {}. Message: \t{}",
                exception.getClass().getName(), request.getRequestURI(), exception.getMessage());

        Map<String, Object> errors = extractConstraintsViolations(exception);

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionResponseDto exceptionResponseDto = responseProvider.getExceptionResponse(
                status, GeneralConstant.Message.NOTE_VALID_DATA_EXCEPTION_MESSAGE, errors);

        return responseEntityProvider.generateResponseEntity(exceptionResponseDto, status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponseDto> handleConstraintViolationException(
            ConstraintViolationException exception, HttpServletRequest request) {

        log.error("Resolve exception: {} for URI {}. Message: \t{}",
                exception.getClass().getName(), request.getRequestURI(), exception.getMessage());

        Map<String, Object> errors = extractConstraintsViolations(exception);

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionResponseDto exceptionResponseDto = responseProvider.getExceptionResponse(
                status, GeneralConstant.Message.NOTE_VALID_DATA_EXCEPTION_MESSAGE, errors);
        return responseEntityProvider.generateResponseEntity(exceptionResponseDto, status);
    }

    private Map<String, Object> extractConstraintsViolations(MethodArgumentNotValidException exception) {

        Map<String, Object> handledErrors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach(error -> {
                    String errorPlace = error instanceof FieldError fieldError ? fieldError.getField() : error.getObjectName();
                    String errorMessage = error.getDefaultMessage();
                    handledErrors.put(errorPlace, errorMessage);
                }
        );

        return handledErrors;
    }

    private Map<String, Object> extractConstraintsViolations(ConstraintViolationException exception) {

        Map<String, Object> handledErrors = new HashMap<>();

        exception.getConstraintViolations().forEach(constraint ->
                handledErrors.put(constraint.getPropertyPath().toString(), constraint.getMessage()));

        return handledErrors;
    }
}
