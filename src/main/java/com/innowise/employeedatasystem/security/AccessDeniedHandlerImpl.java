package com.innowise.employeedatasystem.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.employeedatasystem.dto.AccessDeniedResponseDto;
import com.innowise.employeedatasystem.util.GeneralConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        log.info("Access denied for ip: {}.\nPath: {}", request.getRemoteAddr(), request.getServletPath());
        HttpStatus status = HttpStatus.FORBIDDEN;

        AccessDeniedResponseDto accessDeniedResponseDto = AccessDeniedResponseDto.builder()
                .timestamp(new Date())
                .status(status.name())
                .code(status.value())
                .path(request.getServletPath())
                .message(GeneralConstant.Message.ACCESS_DENIED_EXCEPTION_MESSAGE)
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().print(objectMapper.writeValueAsString(accessDeniedResponseDto));
    }
}
