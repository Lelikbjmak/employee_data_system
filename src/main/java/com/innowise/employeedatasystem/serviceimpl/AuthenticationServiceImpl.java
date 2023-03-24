package com.innowise.employeedatasystem.serviceimpl;

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

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {

            if (userRepository.findByUsername(request.getUsername()).isEmpty()) {
                AuthenticationErrorStatus status = AuthenticationErrorStatus.USERNAME;

                throw new JwtAuthenticationException(status.getStatusMessage(), request.getUsername(), request.getPassword(), status);
            } else {

                User user = userService.findByUsername(request.getUsername());
                AuthenticationErrorStatus status;

                if (!user.isEnabled()) {
                    status = AuthenticationErrorStatus.ENABLED;

                } else if (!user.isAccountNonExpired() | !user.isCredentialsNonExpired()) {
                    status = AuthenticationErrorStatus.EXPIRED;

                } else if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                    status = AuthenticationErrorStatus.PASSWORD;

                } else if (!user.isAccountNonLocked()) {
                    status = AuthenticationErrorStatus.LOCKED;

                } else {
                    status = AuthenticationErrorStatus.ERROR_STATUS;
                }

                throw new JwtAuthenticationException(status.getStatusMessage(), request.getUsername(), request.getPassword(), status);

            }
        }

        HttpStatus status = HttpStatus.ACCEPTED;

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateToken(userDetails);

        return AuthenticationSuccessResponseDto.builder()
                .code(status.value())
                .status(status.name())
                .token(token)
                .timestamp(new Date())
                .message(GeneralConstant.Message.SUCCESSFULLY_AUTHENTICATED_MESSAGE)
                .build();
    }
}
