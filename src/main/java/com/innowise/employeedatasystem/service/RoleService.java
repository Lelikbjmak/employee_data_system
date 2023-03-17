package com.innowise.employeedatasystem.service;

import com.innowise.employeedatasystem.entity.Role;
import com.innowise.employeedatasystem.entity.RoleEnum;

public interface RoleService {

    Role findByRole(RoleEnum role);

}
