package com.innowise.employeedatasystem.service;

import com.innowise.employeedatasystem.entity.Role;
import com.innowise.employeedatasystem.entity.RoleEnum;

import javax.management.relation.RoleInfoNotFoundException;

public interface RoleService {

    Role findByRole(RoleEnum role) throws RoleInfoNotFoundException;

}
