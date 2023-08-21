package com.innowise.employeedatasystem.service;

import com.innowise.employeedatasystem.entity.User;
import com.innowise.employeedatasystem.exception.UserIsNotFoundException;
import com.innowise.employeedatasystem.repo.UserRepository;
import com.innowise.employeedatasystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.argThat;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Argon2PasswordEncoder argon2PasswordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private MockitoSession mockitoSession; // to create mockito session (to init all mocks before each method, like restore cache etc... To control some other working aspects).

    @BeforeTestMethod
    void initSession() {
        mockitoSession = Mockito.mockitoSession()
                .initMocks(this)
                .startMocking();
    }

    @AfterTestMethod
    void destroySession() {
        mockitoSession.finishMocking();
    }

    @Test
    @DisplayName(value = "Context loads test")
    void test() {
        Assertions.assertNotNull(userService);
        Assertions.assertNotNull(userRepository);
        Assertions.assertNotNull(argon2PasswordEncoder);
    }

    @Test
    @DisplayName(value = "Find user by username 'SUCCESS'")
    void successFindUserByUsernameTest() {

        final String username = "testUser";
        final User user = User.builder()
                .username(username)
                .build();

        Optional<User> optionalUser = Optional.of(user);

        Mockito.when(userRepository.findByUsername(username)).thenReturn(optionalUser);

        User findUser = userService.findByUsername(username);
        Assertions.assertNotNull(findUser);
        org.assertj.core.api.Assertions.assertThat(findUser).hasFieldOrPropertyWithValue("username", username);

        Mockito.verify(userRepository, Mockito.times(1)).findByUsername("testUser");
    }

    @Test
    @DisplayName(value = "Find user by username 'FAILED'")
    void failedFindUserByUsernameTest() {

        final String username = "testUser";

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserIsNotFoundException.class, () ->
                userService.findByUsername(username));
    }

    @Test
    @DisplayName(value = "Register user 'SUCCESS'")
    void registerNewUserTest() {

        final String rawPassword = "1111";
        final String encodedPassword = "encodedPassword";

        User user = User.builder()
                .password(rawPassword)
                .build();

        User savedUser = User.builder()
                .password(encodedPassword)
                .build();

        Mockito.when(argon2PasswordEncoder.encode(user.getPassword())).thenReturn(encodedPassword);
        Mockito.when(userRepository.save(user)).thenReturn(savedUser);

        User actualSavedUser = userService.registerUser(user);

        Mockito.verify(argon2PasswordEncoder, Mockito.times(1)).encode(rawPassword);
        Mockito.verify(userRepository, Mockito.times(1)).save(argThat(newSavedUser -> newSavedUser.getPassword().equals(encodedPassword)));

        Assertions.assertEquals(savedUser, actualSavedUser);
    }

    @Test
    @DisplayName(value = "Save User 'SUCCESS'")
    void saveUserTest() {

        User user = User.builder()
                .username("New")
                .build();

        Mockito.when(userRepository.save(user)).thenReturn(Mockito.any(User.class));

        userService.save(user);

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }
}
