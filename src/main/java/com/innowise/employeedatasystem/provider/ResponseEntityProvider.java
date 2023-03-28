package com.innowise.employeedatasystem.provider;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseEntityProvider<T> {

    public ResponseEntity<T> generateResponseEntity(T body, HttpStatus status) {
        return new ResponseEntity<>(body, status);
    }
}
