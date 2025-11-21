package com.openclassrooms.mdd.dto.request;

import com.openclassrooms.mdd.constant.ValidationMessages;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = ValidationMessages.REQUIRED_FIELD)
        String identifier,

        @NotBlank(message = ValidationMessages.REQUIRED_FIELD)
        String password
) {}
