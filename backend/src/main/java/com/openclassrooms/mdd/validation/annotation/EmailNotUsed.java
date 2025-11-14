package com.openclassrooms.mdd.validation.annotation;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.openclassrooms.mdd.constant.ValidationMessages;
import com.openclassrooms.mdd.validation.validator.EmailNotUsedValidator;

@Constraint(validatedBy = EmailNotUsedValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailNotUsed {
    String message() default ValidationMessages.EMAIL_ALREADY_USED;

    Class<?>[] groups() default {};

    Class<? extends jakarta.validation.Payload>[] payload() default {};
}
