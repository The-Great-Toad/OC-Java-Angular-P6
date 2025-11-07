package com.openclassrooms.mdd.dto.response;

import java.util.List;

import com.openclassrooms.mdd.dto.TopicDto;

public record UserProfileResponse(
    String email,
    String name,
    List<TopicDto> subscriptions
) {}
