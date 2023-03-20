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
        public static final String ALL_X = "all";
        public static final String DELETE_X = "delete";
        public static final String EDIT_X = "edit";
        public static final String GET_X = "get";
        public static final String GET_X_BY_ID = "get/{id}";

        public static final String SIGN_IN = "signIn";
    }

    @UtilityClass
    public static class Security {
        public static final String HAS_ROLE_ADMIN = "hasRole('ADMIN')";

        public static final Map<String, String> NOT_SECURED_ROUTES = Map.of(
                ApiPath.SIGN_IN, "/api/auth/signIn"
        );
    }
}