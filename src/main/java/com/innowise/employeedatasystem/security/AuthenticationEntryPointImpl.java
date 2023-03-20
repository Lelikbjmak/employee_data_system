package com.innowise.employeedatasystem.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.employeedatasystem.dto.AuthenticationErrorStatus;
import com.innowise.employeedatasystem.dto.AuthenticationFailedResponseDto;
import com.innowise.employeedatasystem.entity.User;
import com.innowise.employeedatasystem.exception.JwtAuthenticationException;
import com.innowise.employeedatasystem.repo.UserRepository;
import com.innowise.employeedatasystem.util.GeneralConstant;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    private final UserRepository userRepository;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        Map<String, Object> additional = new HashMap<>();

        if (authException instanceof JwtAuthenticationException exception) {

            if (exception.getStatus().equals(AuthenticationErrorStatus.USERNAME)) {

                additional.put(GeneralConstant.Field.USERNAME_FIELD, exception.getUsername());
                additional.put(GeneralConstant.Field.PASSWORD_FIELD, exception.getPassword());

            } else {

                User user = userRepository.findByUsername(exception.getUsername()).orElse(User.builder()
                        .username(exception.getUsername())
                        .password(exception.getPassword())
                        .mail(null)
                        .accountNonExpired(false)
                        .credentialsNonExpired(false)
                        .accountNonLocked(false)
                        .build());

                if (exception.getStatus().equals(AuthenticationErrorStatus.EXPIRED)) {
                    additional.put("credentials expired", user.isCredentialsNonExpired());
                    additional.put("account expired", user.isAccountNonExpired());
                }
            }
        }

        HttpStatus status = HttpStatus.UNAUTHORIZED;

        AuthenticationFailedResponseDto authenticationResponse = AuthenticationFailedResponseDto.builder()
                .timestamp(new Date())
                .message(authException.getMessage())
                .status(status.name())
                .code(status.value())
                .additional(additional)
                .path(request.getRequestURI())
                .build();

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().print(objectMapper.writeValueAsString(authenticationResponse));
    }
}
