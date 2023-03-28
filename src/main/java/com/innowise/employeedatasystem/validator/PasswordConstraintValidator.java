package com.innowise.employeedatasystem.validator;

import com.innowise.employeedatasystem.annotation.ValidPassword;
import com.innowise.employeedatasystem.util.EntityConstant;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    private final Pattern passwordPatternMatcher = Pattern.compile(EntityConstant.Validation.User.PASSWORD_PATTERN);

    @Override
    public boolean isValid(String validatingPassword, ConstraintValidatorContext constraintValidatorContext) {

        if (validatingPassword.isEmpty()) {
            buildConstraintViolation(constraintValidatorContext,
                    EntityConstant.Validation.User.PASSWORD_MANDATORY_CONSTRAINT_MASSAGE);
            return false;
        }

        if (validatingPassword.length() < EntityConstant.Validation.User.MIN_PASSWORD_LENGTH ||
                validatingPassword.length() > EntityConstant.Validation.User.MAX_PASSWORD_LENGTH) {
            buildConstraintViolation(constraintValidatorContext,
                    EntityConstant.Validation.User.PASSWORD_LENGTH_CONSTRAINT_MASSAGE);
            return false;
        }

        if (!passwordPatternMatcher.matcher(validatingPassword).matches()) {
            buildConstraintViolation(constraintValidatorContext,
                    EntityConstant.Validation.User.PASSWORD_NOT_VALID_FORMAT_MESSAGE);
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
