package com.innowise.employeedatasystem.controller;

import com.innowise.employeedatasystem.dto.AuthenticationRequestDto;
import com.innowise.employeedatasystem.dto.AuthenticationSuccessResponseDto;
import com.innowise.employeedatasystem.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "signIn")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AuthenticationSuccessResponseDto authenticateUser(@RequestBody AuthenticationRequestDto authenticationRequest) {
        return authenticationService.authenticateUser(authenticationRequest);
    }

}
