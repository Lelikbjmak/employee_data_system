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
        public static final String  USER_JSON_ROOT_NAME = "user";
    }

}
