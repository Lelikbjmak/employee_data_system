package com.innowise.employeedatasystem.exception;

import lombok.Getter;

import java.time.Instant;

@Getter
public class InvalidTokenException extends Exception {

    private final String token;

    private final Instant timestamp;

    public InvalidTokenException(String message, String token, Instant instant) {
        super(message);
        this.token = token;
        this.timestamp = instant;
    }

}
