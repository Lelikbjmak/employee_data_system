package com.innowise.employeedatasystem.annotation;

import com.innowise.employeedatasystem.util.EntityConstant;
import com.innowise.employeedatasystem.validator.PasswordConstraintValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordConstraintValidator.class)
public @interface ValidPassword {

    String message() default EntityConstant.Validation.User.PASSWORD_NOT_VALID_DEFAULT_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
