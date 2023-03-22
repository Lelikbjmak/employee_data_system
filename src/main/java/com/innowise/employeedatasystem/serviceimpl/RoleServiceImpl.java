package com.innowise.employeedatasystem.serviceimpl;

import com.innowise.employeedatasystem.entity.Role;
import com.innowise.employeedatasystem.entity.RoleEnum;
import com.innowise.employeedatasystem.exception.RoleIsNotFoundException;
import com.innowise.employeedatasystem.repo.RoleRepository;
import com.innowise.employeedatasystem.service.RoleService;
import com.innowise.employeedatasystem.util.GeneralConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findByRole(RoleEnum role) {
        return roleRepository.findByRole(role).orElseThrow(() -> new RoleIsNotFoundException(GeneralConstant.Message.ROLE_IS_NOT_FOUND_EXCEPTION_MESSAGE,
                Instant.now(), Map.of(GeneralConstant.Field.ROLE_FIELD, role)));
    }
}
