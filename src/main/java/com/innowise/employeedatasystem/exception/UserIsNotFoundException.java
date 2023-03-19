package com.innowise.employeedatasystem.exception;

import lombok.Getter;

import java.time.Instant;
import java.util.Map;
import java.util.NoSuchElementException;

@Getter
public class UserIsNotFoundException extends NoSuchElementException {

    private Instant timestamp;

    private Map<String, Object> additional;

    public UserIsNotFoundException(String message, Instant timestamp, Map<String, Object> additional) {
        super(message);
        this.timestamp = timestamp;
        this.additional = additional;
    }
}
