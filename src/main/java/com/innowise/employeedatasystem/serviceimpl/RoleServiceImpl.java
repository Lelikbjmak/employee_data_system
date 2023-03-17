package com.innowise.employeedatasystem.serviceimpl;

import com.innowise.employeedatasystem.entity.Role;
import com.innowise.employeedatasystem.entity.RoleEnum;
import com.innowise.employeedatasystem.exception.RoleIsNotFoundException;
import com.innowise.employeedatasystem.repo.RoleRepository;
import com.innowise.employeedatasystem.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findByRole(RoleEnum role) {
        return roleRepository.findByRole(role).orElseThrow(() -> new RoleIsNotFoundException("Role is not found"));
    }
}
