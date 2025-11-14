package com.openclassrooms.mdd.service.subscription;

import com.openclassrooms.mdd.dto.response.SubscriptionResponse;

import java.util.List;

/**
 * Service interface for managing user subscriptions to topics.
 * Handles business logic for subscription operations.
 */
public interface SubscriptionService {

    /**
     * Subscribe a user to a topic.
     * If the user is already subscribed, this operation is idempotent (no duplicate created).
     *
     * @param userEmail  the email of the user
     * @param topicId the ID of the topic to subscribe to
     * @return the created subscription as a SubscriptionResponse
     */
    SubscriptionResponse subscribe(String userEmail, Long topicId);

    /**
     * Unsubscribe a user from a topic.
     *
     * @param userEmail  the email of the user
     * @param topicId the ID of the topic to unsubscribe from
     * @throws IllegalArgumentException if subscription not found
     */
    void unsubscribe(String userEmail, Long topicId);

    /**
     * Get all subscriptions for a specific user.
     *
     * @param userEmail the email of the user
     * @return list of subscriptions for the user
     */
    List<SubscriptionResponse> getUserSubscriptions(String userEmail);
}
