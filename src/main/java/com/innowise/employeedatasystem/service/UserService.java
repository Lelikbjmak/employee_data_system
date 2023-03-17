package com.innowise.employeedatasystem.service;

import com.innowise.employeedatasystem.entity.User;

public interface UserService {

    User findByUsername(String username);

    User save(User user);

    User registerUser(User user);
}
