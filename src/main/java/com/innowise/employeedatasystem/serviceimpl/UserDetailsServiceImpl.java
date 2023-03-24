package com.innowise.employeedatasystem.serviceimpl;

import com.innowise.employeedatasystem.entity.User;
import com.innowise.employeedatasystem.repo.UserRepository;
import com.innowise.employeedatasystem.security.ApplicationUserDetails;
import com.innowise.employeedatasystem.util.GeneralConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(GeneralConstant.Message.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE));

        return new ApplicationUserDetails(user);
    }

}
