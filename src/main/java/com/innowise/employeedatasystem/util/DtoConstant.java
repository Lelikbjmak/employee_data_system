package com.innowise.employeedatasystem.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DtoConstant {

    @UtilityClass
    public static class Employee {
        public static final String EMPLOYEE_JSON_ROOT_NAME = "employee";
        public static final String DELETED_EMPLOYEE_JSON_ROOT_NAME = "deletedEmployee";
        public static final String UPDATED_EMPLOYEE_JSON_ROOT_NAME = "updatedEmployee";
    }

    @UtilityClass
    public static class User {
        public static final String USER_JSON_ROOT_NAME = "user";
    }

    @UtilityClass
    public static class Json {
        public static final String JSON_EMPLOYEE_DTO_NAME = "employeeDto";
        public static final String JSON_USER_DTO_NAME = "userDto";
        public static final String JSON_EMPLOYEE_NAME = "employee";
        public static final String JSON_USER_NAME = "user";
        public static final String TIMESTAMP_FIELD_NAME = "timestamp";
        public static final String STATUS_FIELD_NAME = "status";
        public static final String CODE_FIELD_NAME = "code";
        public static final String MESSAGE_FIELD_NAME = "message";
        public static final String PATH_FIELD_NAME = "path";
        public static final String ADDITIONAL_CONTENT_FIELD_NAME = "additional";
        public static final String TOKEN_FIELD_NAME = "token";
        public static final String IS_DELETED_FIELD_NAME = "isDeleted";
        public static final String IS_UPDATED_FIELD_NAME = "isUpdated";
    }
}
