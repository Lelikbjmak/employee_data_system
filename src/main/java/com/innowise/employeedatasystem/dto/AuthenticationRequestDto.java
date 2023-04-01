package com.innowise.employeedatasystem.dto;

import com.innowise.employeedatasystem.util.EntityConstant;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AuthenticationRequestDto(
        @NotBlank(message = EntityConstant.Validation.User.USERNAME_MANDATORY_CONSTRAINT_MASSAGE) String username,
        @NotBlank(message = EntityConstant.Validation.User.PASSWORD_MANDATORY_CONSTRAINT_MASSAGE) String password) {

}
