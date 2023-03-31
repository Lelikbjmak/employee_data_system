package com.innowise.employeedatasystem.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.innowise.employeedatasystem.annotation.ValidPassword;
import com.innowise.employeedatasystem.annotation.ValidUsername;
import com.innowise.employeedatasystem.util.DtoConstant;
import com.innowise.employeedatasystem.util.EntityConstant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = DtoConstant.User.USER_JSON_ROOT_NAME)
public class RegistrationUserDto {

    @ValidUsername
    private String username;

    @ValidPassword
    private String password;

    @Email(regexp = EntityConstant.Validation.User.MAIL_PATTERN,
            message = EntityConstant.Validation.User.MAIL_NOT_VALID_FORMAT_CONSTRAINT_MESSAGE)
    private String mail;

    @NotNull(message = EntityConstant.Validation.User.ROLE_SET_IS_MANDATORY_CONSTRAINT_MESSAGE)
    private Set<String> roles;
}
