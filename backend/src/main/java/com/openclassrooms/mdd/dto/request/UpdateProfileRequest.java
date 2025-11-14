package com.openclassrooms.mdd.dto.request;

import com.openclassrooms.mdd.constant.Regex;
import com.openclassrooms.mdd.constant.ValidationMessages;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(
    @Email(message = ValidationMessages.INVALID_FORMAT)
    String email,

    String username,

    @Size(min = 8, message = ValidationMessages.PASSWORD_TOO_SHORT)
    @Pattern(
        regexp = Regex.PASSWORD_PATTERN,
        message = ValidationMessages.PASSWORD_INVALID_FORMAT)
    String password
) {}
