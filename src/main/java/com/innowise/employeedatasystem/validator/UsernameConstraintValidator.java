package com.innowise.employeedatasystem.validator;

import com.innowise.employeedatasystem.annotation.ValidUsername;
import com.innowise.employeedatasystem.repo.UserRepository;
import com.innowise.employeedatasystem.util.EntityConstant;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.regex.Pattern;

@RequiredArgsConstructor
public class UsernameConstraintValidator implements ConstraintValidator<ValidUsername, String> {

    private final Pattern usernamePattern = Pattern.compile(EntityConstant.Validation.User.USERNAME_PATTERN);

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        int usernameLength = username.length();

        if (username.isEmpty()) {
            buildConstraintViolation(constraintValidatorContext,
                    EntityConstant.Validation.User.USERNAME_MANDATORY_CONSTRAINT_MASSAGE);
            return false;
        }

        if (usernameLength < EntityConstant.Validation.User.MIN_USERNAME_LENGTH ||
                usernameLength > EntityConstant.Validation.User.MAX_USERNAME_LENGTH) {
            buildConstraintViolation(constraintValidatorContext,
                    EntityConstant.Validation.User.USERNAME_LENGTH_CONSTRAINT_MASSAGE);
            return false;
        }

        if (!usernamePattern.matcher(username).matches()) {
            buildConstraintViolation(constraintValidatorContext,
                    EntityConstant.Validation.User.USERNAME_NOT_VALID_FORMAT_MESSAGE);
            return false;
        }

        if (userRepository.findByUsername(username).isPresent()) {
            buildConstraintViolation(constraintValidatorContext,
                    EntityConstant.Validation.User.USERNAME_ALREADY_USED_MESSAGE);
            return false;
        }

        return true;
    }

    private void buildConstraintViolation(ConstraintValidatorContext context, String message) {
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
    }

}
