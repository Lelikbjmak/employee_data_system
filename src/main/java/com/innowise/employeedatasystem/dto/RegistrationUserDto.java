package com.innowise.employeedatasystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationUserDto {

    private String username;

    private String password;

    private String mail;

    private Set<String> roles;

}
