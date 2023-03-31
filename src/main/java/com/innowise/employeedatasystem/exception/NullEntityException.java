package com.innowise.employeedatasystem.exception;

import lombok.Getter;

import java.time.Instant;

@Getter
public class NullEntityException extends RuntimeException {

    private final Instant timestamp;

    private final String method;

    private final String className;

    public NullEntityException(String message, String method, String className) {
        super(message);
        this.timestamp = Instant.now();
        this.method = method;
        this.className = className;
    }

}
