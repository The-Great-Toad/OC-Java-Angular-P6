package com.openclassrooms.mdd.dto.request;

import com.openclassrooms.mdd.constant.ValidationMessages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCommentRequest(
        @NotBlank(message = ValidationMessages.REQUIRED_FIELD)
        @Size(min = 1, max = 2000, message = ValidationMessages.COMMENT_CONTENT_SIZE)
        String content
) {
}
