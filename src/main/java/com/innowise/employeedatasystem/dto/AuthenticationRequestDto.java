package com.innowise.employeedatasystem.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthenticationRequestDto {

    private String username;

    private String password;

}
