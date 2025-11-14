package com.openclassrooms.mdd.validation.validator;

import com.openclassrooms.mdd.repository.UserRepository;
import com.openclassrooms.mdd.validation.annotation.EmailNotUsed;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailNotUsedValidator implements ConstraintValidator<EmailNotUsed, String> {

    private final UserRepository userRepository;

    public EmailNotUsedValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        /* Annotation @NotBlank takes care of those cases */
        if (email == null || email.isBlank()) {
            return true;
        }
        return !userRepository.existsByEmail(email);
    }
}
