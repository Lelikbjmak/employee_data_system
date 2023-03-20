package com.innowise.employeedatasystem.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.employeedatasystem.dto.AuthenticationSuccessResponseDto;
import com.innowise.employeedatasystem.exception.InvalidTokenException;
import com.innowise.employeedatasystem.service.JwtService;
import com.innowise.employeedatasystem.util.GeneralConstant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authenticationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authenticationHeader == null || !authenticationHeader.startsWith(GeneralConstant.Feature.BEARER_TOKEN_HEADER)) {
            log.info("Bearer token is null");
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = authenticationHeader.substring(GeneralConstant.Feature.BEARER_TOKEN_START_INDEX);

        try {

            String username = jwtService.extractUsername(jwtToken);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtService.isJwtTokenValid(jwtToken, userDetails)) {

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

        } catch (InvalidTokenException exception) {

            HttpStatus status = HttpStatus.UNAUTHORIZED;

            log.info("Error jwtToken for IP: " + getClientIpAddress(request));

            AuthenticationSuccessResponseDto authenticationResponse = AuthenticationSuccessResponseDto.builder()
                    .timestamp(new Date())
                    .code(status.value())
                    .status(status.name())
                    .token(jwtToken)
                    .message(exception.getMessage())
                    .build();

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(status.value());
            response.getOutputStream().print(objectMapper.writeValueAsString(authenticationResponse));
            return;
        }

        filterChain.doFilter(request, response);

    }

    private String getClientIpAddress(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}