package com.innowise.employeedatasystem.dto;

import com.innowise.employeedatasystem.util.GeneralConstant;
import lombok.Getter;

@Getter
public enum AuthenticationErrorStatus {

    PASSWORD(GeneralConstant.Message.AUTHENTICATION_ERROR_INCORRECT_PASSWORD_MESSAGE),
    USERNAME(GeneralConstant.Message.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE),
    ENABLED(GeneralConstant.Message.AUTHENTICATION_ERROR_ACCOUNT_DISABLED_MESSAGE),
    EXPIRED(GeneralConstant.Message.AUTHENTICATION_ERROR_ACCOUNT_OR_CREDENTIALS_EXPIRED_MESSAGE),
    LOCKED(GeneralConstant.Message.AUTHENTICATION_ERROR_ACCOUNT_LOCKED_MESSAGE),
    ERROR_STATUS(GeneralConstant.Message.AUTHENTICATION_DEFAULT_ERROR_MESSAGE);

    private final String statusMessage;

    AuthenticationErrorStatus(String statusMessage) {
        this.statusMessage = statusMessage;
    }

}
