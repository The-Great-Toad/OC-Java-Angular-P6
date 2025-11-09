package com.openclassrooms.mdd.dto.request;

import com.openclassrooms.mdd.constant.ValidationMessages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePostRequest(
    @NotNull(message = ValidationMessages.REQUIRED_FIELD)
    Long topicId,

    @NotBlank(message = ValidationMessages.REQUIRED_FIELD)
    @Size(min = 3, max = 255, message = ValidationMessages.TITLE_SIZE)
    String title,

    @NotBlank(message = ValidationMessages.REQUIRED_FIELD)
    @Size(min = 10, message = ValidationMessages.CONTENT_SIZE)
    String content
) {}
