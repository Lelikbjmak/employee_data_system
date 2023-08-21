package com.innowise.employeedatasystem.service.impl;

import com.innowise.employeedatasystem.entity.User;
import com.innowise.employeedatasystem.repo.UserRepository;
import com.innowise.employeedatasystem.security.ApplicationUserDetails;
import com.innowise.employeedatasystem.util.GeneralConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("Load user by username: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(GeneralConstant.Message.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE));

        return new ApplicationUserDetails(user);
    }

}
