package com.innowise.employeedatasystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.innowise.employeedatasystem.util.DtoConstant;
import com.innowise.employeedatasystem.util.EntityConstant;
import lombok.Builder;

import java.util.Set;

@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({EntityConstant.Column.ID_FIELD,
        EntityConstant.Column.USERNAME_FIELD,
        EntityConstant.Column.MAIL_FIELD,
        EntityConstant.Column.ROLES,
        EntityConstant.Column.USER_ENABLED_FIELD,
        EntityConstant.Column.USER_ACCOUNT_NON_LOCKED_FIELD,
        EntityConstant.Column.USER_ACCOUNT_NON_EXPIRED_FIELD,
        EntityConstant.Column.USER_CREDENTIALS_NON_EXPIRED_FIELD})
@JsonRootName(value = DtoConstant.User.USER_JSON_ROOT_NAME)
public record UserDto(Long id, String username, String mail, Set<String> roles,
                      boolean enabled, boolean accountNonExpired, boolean accountNonLocked,
                      boolean credentialsNonExpired) {
}
