package com.innowise.employeedatasystem.serviceimpl;

import com.innowise.employeedatasystem.entity.User;
import com.innowise.employeedatasystem.repo.UserRepository;
import com.innowise.employeedatasystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final Argon2PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("sad"));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return save(user);
    }
}
