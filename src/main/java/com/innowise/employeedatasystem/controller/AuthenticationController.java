package com.innowise.employeedatasystem.controller;

import com.innowise.employeedatasystem.dto.AuthenticationRequestDto;
import com.innowise.employeedatasystem.dto.AuthenticationSuccessResponseDto;
import com.innowise.employeedatasystem.service.AuthenticationService;
import com.innowise.employeedatasystem.util.ApiConstant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = ApiConstant.ApiMappings.AUTH_ROUT)
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = ApiConstant.ApiPath.SIGN_IN)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AuthenticationSuccessResponseDto authenticateUser(@RequestBody @Valid AuthenticationRequestDto authenticationRequest) {
        return authenticationService.authenticateUser(authenticationRequest);
    }

}
