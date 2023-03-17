package com.innowise.employeedatasystem.service;

import com.innowise.employeedatasystem.dto.AuthenticationRequestDto;
import com.innowise.employeedatasystem.dto.AuthenticationSuccessResponseDto;

public interface AuthenticationService {

    AuthenticationSuccessResponseDto authenticateUser(AuthenticationRequestDto request);

}
