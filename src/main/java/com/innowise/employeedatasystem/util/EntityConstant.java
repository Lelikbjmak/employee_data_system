package com.innowise.employeedatasystem.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EntityConstant {

    @UtilityClass
    public static class Table {
        public static final String TABLE_USERS = "users";
        public static final String TABLE_USERS_ROLES = "users_roles";
        public static final String TABLE_EMPLOYEES = "employees";
        public static final String TABLE_ROLES = "roles";
    }

    @UtilityClass
    public static class Column {
        public static final String USER_ID = "user_id";
        public static final String ROLE_ID = "role_id";
        public static final String EMPLOYEE = "employee";
        public static final String ROLES = "roles";
        public static final String USER_ENABLED_FIELD = "enabled";
        public static final String USER_ACCOUNT_NON_EXPIRED_FIELD = "accountNonExpired";
        public static final String USER_ACCOUNT_NON_LOCKED_FIELD = "accountNonLocked";
        public static final String USER_CREDENTIALS_NON_EXPIRED_FIELD = "credentialsNonExpired";
        public static final String USERNAME_FIELD = "username";
        public static final String ROLE_FIELD = "role";
        public static final String PASSWORD_FIELD = "password";
        public static final String MAIL_FIELD = "mail";
        public static final String ID_FIELD = "id";
        public static final String EMPLOYEE_FIRST_NAME_FIELD = "firstName";
        public static final String EMPLOYEE_LAST_NAME_FIELD = "lastName";
        public static final String EMPLOYEE_MIDDLE_NAME_FIELD = "middleName";
        public static final String EMPLOYEE_HIRE_DATE_FIELD = "hireDate";
    }

    @UtilityClass
    public static class Validation {

        @UtilityClass
        public static class General {
            public static final String ID_MUST_GREATER_THAN_ZERO_CONSTRAINT_MESSAGE = "Id must be positive number";
        }

        @UtilityClass
        public static class User {
            public static final String USERNAME_MANDATORY_CONSTRAINT_MASSAGE = "Username is mandatory.";
            public static final String USERNAME_LENGTH_CONSTRAINT_MASSAGE = "Username must contain at least 5 and no more than 25 chars.";
            public static final int MIN_USERNAME_LENGTH = 5;
            public static final int MAX_USERNAME_LENGTH = 25;
            public static final String USERNAME_NOT_VALID_DEFAULT_MESSAGE = "Username is not valid.";
            public static final String USERNAME_NOT_VALID_FORMAT_MESSAGE = "Username must contain only letters: [a-Z], digits: [0-9] and underscore [_].";
            public static final String USERNAME_ALREADY_USED_MESSAGE = "Username already used.";
            public static final String USERNAME_PATTERN = "^[A-Za-z]\\w{5,24}$";

            public static final String PASSWORD_MANDATORY_CONSTRAINT_MASSAGE = "Password is mandatory.";
            public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,25}$";
            public static final String PASSWORD_LENGTH_CONSTRAINT_MASSAGE = "Password must contain at least 8 and no more than 25 symbols.";
            public static final String PASSWORD_NOT_VALID_FORMAT_MESSAGE = "Password must contain at least 1 [A-Z], 1 [a-z], 1 [0-9] symbols.";
            public static final int MIN_PASSWORD_LENGTH = 8;
            public static final int MAX_PASSWORD_LENGTH = 25;
            public static final String PASSWORD_NOT_VALID_DEFAULT_MESSAGE = "Password is not valid.";

            public static final String MAIL_NOT_VALID_FORMAT_CONSTRAINT_MESSAGE = "Must be a well-formed email address.";
            public static final String MAIL_PATTERN = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}";

            public static final String ROLE_SET_IS_MANDATORY_CONSTRAINT_MESSAGE = "Role set is mandatory.";

            public static final String USER_MANDATORY_FOR_REGISTRATION_CONSTRAINT_MESSAGE = "User is mandatory for registration.";
        }

        @UtilityClass
        public static class Employee {
            public static final String HIRE_DATE_MANDATORY_CONSTRAINT_MESSAGE = "Hire date is mandatory.";
            public static final String HIRE_DATE_EXCEPTION_MESSAGE = "Hire date must be a date from the past or present.";
            public static final String FIRST_NAME_MANDATORY_CONSTRAINT_MESSAGE = "First name is mandatory.";
            public static final String LAST_NAME_MANDATORY_CONSTRAINT_MESSAGE = "Last name is mandatory.";
            public static final String MIDDLE_NAME_MANDATORY_CONSTRAINT_MESSAGE = "Middle name is mandatory.";

            public static final String EMPLOYEE_MANDATORY_FOR_REGISTRATION_CONSTRAINT_MESSAGE = "Employee is mandatory for registration.";
            public static final String ID_MANDATORY_TO_UPDATE_CONSTRAINT_MESSAGE = "Field id is required to edit Employee.";
        }
    }
}
