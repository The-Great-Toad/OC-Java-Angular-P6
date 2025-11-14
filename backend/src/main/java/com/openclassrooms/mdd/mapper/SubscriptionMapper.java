package com.openclassrooms.mdd.mapper;

import org.springframework.stereotype.Component;

import com.openclassrooms.mdd.dto.response.SubscriptionResponse;
import com.openclassrooms.mdd.model.Subscription;

@Component
public class SubscriptionMapper {

    /**
     * Maps a Subscription entity to a SubscriptionResponse DTO.
     *
     * @param subscription the subscription entity
     * @return the subscription response DTO
     */
    public SubscriptionResponse mapToResponse(Subscription subscription) {
        return new SubscriptionResponse(
                subscription.getId(),
                subscription.getTopic().getId(),
                subscription.getTopic().getName(),
                subscription.getTopic().getDescription(),
                subscription.getSubscribedAt()
        );
    }
}
