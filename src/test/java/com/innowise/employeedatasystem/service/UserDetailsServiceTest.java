package com.innowise.employeedatasystem.service;

import com.innowise.employeedatasystem.entity.User;
import com.innowise.employeedatasystem.repo.UserRepository;
import com.innowise.employeedatasystem.security.ApplicationUserDetails;
import com.innowise.employeedatasystem.serviceimpl.UserDetailsServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    @Order(1)
    @DisplayName(value = "Context loads")
    void contextLoadsTest() {
        Assertions.assertNotNull(userRepository);
        Assertions.assertNotNull(userDetailsService);
    }

    @Test
    @Order(2)
    @DisplayName(value = "Load user by username")
    void mustReturnUserByUsernameTest() {

        final String username = "username";

        User user = User.builder()
                .username(username)
                .build();

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails foundUser = userDetailsService.loadUserByUsername(username);

        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(username);
        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(new ApplicationUserDetails(user).getUsername(), foundUser.getUsername());
    }

    @Test
    @Order(3)
    @DisplayName(value = "Load user by username. UserNotFoundException is thrown.")
    void mustThrowExceptionUserByUsernameTest() {

        final String username = "username";

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Assertions.assertThrows(UsernameNotFoundException.class, () ->
                userDetailsService.loadUserByUsername(username));

        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(username);
    }
}
