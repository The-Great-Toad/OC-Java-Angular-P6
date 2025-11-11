package com.openclassrooms.mdd.dto.response;

import com.openclassrooms.mdd.dto.TopicDto;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponse(
    Long id,
    String title,
    String content,
    String author,
    TopicDto topic,
    LocalDateTime createdAt,
    List<CommentResponse> comments
) {}
