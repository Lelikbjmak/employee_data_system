package com.innowise.employeedatasystem.serviceimpl;

import com.innowise.employeedatasystem.entity.RoleEnum;
import com.innowise.employeedatasystem.entity.User;
import com.innowise.employeedatasystem.exception.NullEntityException;
import com.innowise.employeedatasystem.exception.UserIsNotFoundException;
import com.innowise.employeedatasystem.repo.UserRepository;
import com.innowise.employeedatasystem.service.UserService;
import com.innowise.employeedatasystem.util.GeneralConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private final Argon2PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UserIsNotFoundException(GeneralConstant.Message.USER_IS_NOT_FOUND_EXCEPTION_MESSAGE, Instant.now(), Map.of("Username", username)));
    }

    @Override
    public User save(User user) {
        if (user == null)
            throw new NullEntityException(GeneralConstant.Message.NULL_ENTITY_EXCEPTION_MESSAGE,
                    Thread.currentThread().getStackTrace()[1].getMethodName(), RoleEnum.class.getName());
        return userRepository.save(user);
    }

    @Override
    public User registerUser(User user) {
        if (user == null)
            throw new NullEntityException(GeneralConstant.Message.NULL_ENTITY_EXCEPTION_MESSAGE,
                    Thread.currentThread().getStackTrace()[1].getMethodName(), RoleEnum.class.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return save(user);
    }
}
