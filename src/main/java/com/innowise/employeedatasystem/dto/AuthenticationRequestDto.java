package com.innowise.employeedatasystem.dto;

import com.innowise.employeedatasystem.util.EntityConstant;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequestDto {

    @NotBlank(message = EntityConstant.Validation.User.USERNAME_MANDATORY_CONSTRAINT_MASSAGE)
    private String username;


    @NotBlank(message = EntityConstant.Validation.User.PASSWORD_MANDATORY_CONSTRAINT_MASSAGE)
    private String password;
}
