package com.innowise.employeedatasystem.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GeneralConstant {

    @UtilityClass
    public static class Message {

        public static final String USER_IS_NOT_FOUND_EXCEPTION_MESSAGE = "User is not found";
        public static final String USERNAME_NOT_FOUND_EXCEPTION_MESSAGE = "Username not found.";

        public static final String EMPLOYEE_IS_NOT_FOUND_BY_ID_EXCEPTION_MESSAGE = "Employee is not found.";
        public static final String EMPLOYEE_SUCCESSFULLY_ADDED_MESSAGE = "Successfully added employees.";

        public static final String SUCCESSFULLY_AUTHENTICATED_MESSAGE = "Successfully authenticated.";
        public static final String AUTHENTICATION_ERROR_INCORRECT_PASSWORD_MESSAGE = "Incorrect password.";
        public static final String AUTHENTICATION_ERROR_ACCOUNT_DISABLED_MESSAGE = "Account isn't enabled.";
        public static final String AUTHENTICATION_ERROR_ACCOUNT_OR_CREDENTIALS_EXPIRED_MESSAGE = "Account or Credentials are expired.";
        public static final String AUTHENTICATION_ERROR_ACCOUNT_LOCKED_MESSAGE = "Account is locked.";
        public static final String AUTHENTICATION_DEFAULT_ERROR_MESSAGE = "Authentication Error.";

        public static final String ACCESS_DENIED_EXCEPTION_MESSAGE = "Access denied. Not compliant authorities.";

        public static final String ROLE_IS_NOT_FOUND_EXCEPTION_MESSAGE = "Role is not found.";

        public static final String NOTE_VALID_DATA_EXCEPTION_MESSAGE = "Not valid derived data.";

        public static final String NULL_ENTITY_EXCEPTION_MESSAGE = "Entity is null.";
    }

    @UtilityClass
    public static class Feature {
        public static final String JWT_TOKEN_SECRET_KEY = "2B4D6251655468576D5A7134743777217A25432A462D4A404E635266556A586E";
        public static final long JWT_TOKEN_VALIDITY = 1000L * 60 * 60 * 2;  // 2 hours

        public static final String BEARER_TOKEN_HEADER = "Bearer ";
        public static final int BEARER_TOKEN_START_INDEX = 7;
    }

    @UtilityClass
    public static class Field {
        public static final String USERNAME_FIELD = "username";
        public static final String ROLE_FIELD = "role";
        public static final String PASSWORD_FIELD = "password";
        public static final String ID_FIELD = "id";
        public static final String EMPLOYEE_FIRST_NAME_FIELD = "firstName";
        public static final String EMPLOYEE_LAST_NAME_FIELD = "lastName";
        public static final String EMPLOYEE_MIDDLE_NAME_FIELD = "middleName";
        public static final String EMPLOYEE_HIRE_DATE_FIELD = "hireDate";
    }
}
