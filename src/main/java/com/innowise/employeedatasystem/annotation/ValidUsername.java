package com.innowise.employeedatasystem.annotation;

import com.innowise.employeedatasystem.util.EntityConstant;
import com.innowise.employeedatasystem.validator.UsernameConstraintValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidUsername {

    String message() default EntityConstant.Validation.User.USERNAME_NOT_VALID_DEFAULT_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
