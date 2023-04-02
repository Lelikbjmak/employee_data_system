package com.innowise.employeedatasystem.util;


import lombok.experimental.UtilityClass;

@UtilityClass
public class TestConstant {

    public static class ApiRoutes {
        public static final String AUTH_ROUTE = "/api/auth/signIn";
        public static final String GET_X_BY_ID = "/api/employee/get/id/{id}";
        public static final String GET_X_BY_USERNAME = "/api/employee/get/username/{username}";
        public static final String GET_ALL_X = "/api/employee/all";
        public static final String DELETE_X = "/api/employee/delete/id/{id}";
        public static final String DELETE_ALL_X = "/api/employee/delete/all";
        public static final String EDIT_X = "/api/employee/edit/id/{id}";
        public static final String EDIT_ALL_X = "/api/employee/edit/all";
        public static final String ADD_X = "/api/employee/add";
        public static final String ADD_ALL_X = "/api/employee/add/all";
    }

    public static class Message {
        public static final String NOT_AUTHENTICATED_MESSAGE = "Full authentication is required to access this resource";
    }
}
