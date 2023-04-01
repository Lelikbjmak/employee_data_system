package com.innowise.employeedatasystem.controller;

import com.innowise.employeedatasystem.dto.AuthenticationRequestDto;
import com.innowise.employeedatasystem.dto.AuthenticationSuccessResponseDto;
import com.innowise.employeedatasystem.service.AuthenticationService;
import com.innowise.employeedatasystem.util.ApiConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = ApiConstant.ApiMappings.AUTH_ROUT)
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = ApiConstant.ApiPath.SIGN_IN)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AuthenticationSuccessResponseDto authenticateUser(@RequestBody @Valid AuthenticationRequestDto authenticationRequest,
                                                             HttpServletRequest request) {
        log.info("Request: {} is processing...", request.getRequestURI());
        return authenticationService.authenticateUser(authenticationRequest);
    }

}
