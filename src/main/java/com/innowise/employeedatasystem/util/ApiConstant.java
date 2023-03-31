package com.innowise.employeedatasystem.util;

import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class ApiConstant {

    @UtilityClass
    public static class ApiMappings {
        public static final String AUTH_ROUT = "/api/auth";
        public static final String EMPLOYEE_ROUT = "/api/employee";
    }

    @UtilityClass
    public static final class ApiPath {

        public static final String ADD_X = "add";
        public static final String ADD_ALL_X = "add/all";
        public static final String ALL_X = "all";
        public static final String DELETE_ALL_X = "delete/all";
        public static final String DELETE_X = "delete/id/{id}";
        public static final String EDIT_X = "edit/id/{id}";
        public static final String EDIT_ALL_X = "edit/all";
        public static final String GET_X_BY_USERNAME = "get/username/{username}";
        public static final String GET_X_BY_ID = "get/id/{id}";
        public static final String SIGN_IN = "signIn";
    }

    @UtilityClass
    public static class Security {
        public static final String HAS_ROLE_ADMIN = "hasRole('ADMIN')";

        public static final Map<String, String> NOT_SECURED_ROUTES = Map.of(
                ApiPath.SIGN_IN, "/api/auth/signIn"
        );
    }

    @UtilityClass
    public static class Validation {
        public static final String REQUEST_BODY_MISSED_MESSAGE = "Required request body is missing.";
    }
}