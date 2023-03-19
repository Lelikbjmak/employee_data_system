package com.innowise.employeedatasystem.exception;

import lombok.Getter;

import java.time.Instant;
import java.util.Map;
import java.util.NoSuchElementException;

@Getter
public class RoleIsNotFoundException extends NoSuchElementException {

    private Instant timestamp;

    private Map<String, Object> additional;

    public RoleIsNotFoundException(String message, Instant instant, Map<String, Object> additional) {
        super(message);
        this.timestamp = instant;
        this.additional = additional;
    }
}
