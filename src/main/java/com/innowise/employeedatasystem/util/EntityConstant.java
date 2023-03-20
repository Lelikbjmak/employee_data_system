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
        public static final String ID = "id";
        public static final String USER_ID = "user_id";
        public static final String ROLE_ID = "role_id";
        public static final String EMPLOYEE = "employee";
        public static final String ROLES = "roles";
    }
}
