package com.innowise.employeedatasystem.service.impl;

import com.innowise.employeedatasystem.dto.AuthenticationErrorStatus;
import com.innowise.employeedatasystem.dto.AuthenticationRequestDto;
import com.innowise.employeedatasystem.dto.AuthenticationSuccessResponseDto;
import com.innowise.employeedatasystem.entity.User;
import com.innowise.employeedatasystem.exception.JwtAuthenticationException;
import com.innowise.employeedatasystem.repo.UserRepository;
import com.innowise.employeedatasystem.service.AuthenticationService;
import com.innowise.employeedatasystem.service.JwtService;
import com.innowise.employeedatasystem.service.UserService;
import com.innowise.employeedatasystem.util.GeneralConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final UserDetailsService userDetailsService;

    private final UserService userService;
    private final Argon2PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationSuccessResponseDto authenticateUser(AuthenticationRequestDto request) {

        log.info("Trying to authenticate user: {}", request.username());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()
                    )
            );
        } catch (AuthenticationException e) {

            log.error("Error during authentication user {}. \nCause: {}", request.username(), e.getMessage());

            if (userRepository.findByUsername(request.username()).isEmpty()) {
                AuthenticationErrorStatus status = AuthenticationErrorStatus.USERNAME;
                log.error("Error during authentication user. Username: {}, password {}.\n" + status.getStatusMessage(),
                        request.username(), request.password());
                throw new JwtAuthenticationException(status.getStatusMessage(), request.username(), request.password(), status);
            } else {

                User user = userService.findByUsername(request.username());
                AuthenticationErrorStatus status;

                if (!user.isEnabled()) {
                    status = AuthenticationErrorStatus.ENABLED;
                    log.error("Error during authentication user. Username: {}, password {}.\n" + status.getStatusMessage(),
                            request.username(), request.password());
                } else if (!user.isAccountNonExpired() || !user.isCredentialsNonExpired()) {
                    status = AuthenticationErrorStatus.EXPIRED;
                    log.error("Error during authentication user. Username: {}, password {}.\n" + status.getStatusMessage(),
                            request.username(), request.password());
                } else if (!passwordEncoder.matches(request.password(), user.getPassword())) {
                    status = AuthenticationErrorStatus.PASSWORD;
                    log.error("Error during authentication user. Username: {}, password {}.\n" + status.getStatusMessage(),
                            request.username(), request.password());
                } else if (!user.isAccountNonLocked()) {
                    status = AuthenticationErrorStatus.LOCKED;
                    log.error("Error during authentication user. Username: {}, password {}.\n" + status.getStatusMessage(),
                            request.username(), request.password());
                } else {
                    status = AuthenticationErrorStatus.ERROR_STATUS;
                    log.error("Error during authentication user. Username: {}, password {}.\n" + status.getStatusMessage(),
                            request.username(), request.password());
                }

                throw new JwtAuthenticationException(status.getStatusMessage(), request.username(), request.password(), status);
            }
        }

        HttpStatus status = HttpStatus.ACCEPTED;

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        String token = jwtService.generateToken(userDetails);

        log.info("Successfully authenticate User {}", request.username());

        return AuthenticationSuccessResponseDto.builder()
                .code(status.value())
                .status(status.name())
                .token(token)
                .timestamp(new Date())
                .message(GeneralConstant.Message.SUCCESSFULLY_AUTHENTICATED_MESSAGE)
                .build();
    }
}
