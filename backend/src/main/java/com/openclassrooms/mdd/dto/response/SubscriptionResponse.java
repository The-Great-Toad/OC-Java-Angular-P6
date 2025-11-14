package com.openclassrooms.mdd.dto.response;

import java.time.LocalDateTime;

public record SubscriptionResponse(
        Long id,
        Long topicId,
        String topicName,
        String topicDescription,
        LocalDateTime subscribedAt
) {
}
