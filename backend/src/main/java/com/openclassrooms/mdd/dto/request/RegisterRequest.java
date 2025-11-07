package com.openclassrooms.mdd.dto.request;

import com.openclassrooms.mdd.constant.Regex;
import com.openclassrooms.mdd.constant.ValidationMessages;
import com.openclassrooms.mdd.validation.annotation.EmailNotUsed;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD)
    String name,

    @NotBlank(message = ValidationMessages.REQUIRED_FIELD)
    @Email(message = ValidationMessages.INVALID_FORMAT)
    @EmailNotUsed
    String email,

    @NotBlank(message = ValidationMessages.REQUIRED_FIELD)
    @Size(min = 8, message = ValidationMessages.PASSWORD_TOO_SHORT)
    @Pattern(
        regexp = Regex.PASSWORD_PATTERN,
        message = ValidationMessages.PASSWORD_INVALID_FORMAT)
    String password
) {}
