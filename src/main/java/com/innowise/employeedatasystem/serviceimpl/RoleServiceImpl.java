package com.innowise.employeedatasystem.serviceimpl;

import com.innowise.employeedatasystem.entity.Role;
import com.innowise.employeedatasystem.entity.RoleEnum;
import com.innowise.employeedatasystem.exception.NullEntityException;
import com.innowise.employeedatasystem.exception.RoleIsNotFoundException;
import com.innowise.employeedatasystem.repo.RoleRepository;
import com.innowise.employeedatasystem.service.RoleService;
import com.innowise.employeedatasystem.util.EntityConstant;
import com.innowise.employeedatasystem.util.GeneralConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findByRole(RoleEnum role) {

        if (role == null)
            throw new NullEntityException(GeneralConstant.Message.NULL_ENTITY_EXCEPTION_MESSAGE,
                    Thread.currentThread().getStackTrace()[1].getMethodName(), RoleEnum.class.getName());

        log.info("Find Role by name: {}", role.name());

        Role foundRole = roleRepository.findByRole(role).orElseThrow(() -> new RoleIsNotFoundException(GeneralConstant.Message.ROLE_IS_NOT_FOUND_EXCEPTION_MESSAGE,
                Instant.now(), Map.of(EntityConstant.Column.ROLE_FIELD, role)));

        log.info("Successfully found Role by name: {}", role.name());

        return foundRole;
    }
}
