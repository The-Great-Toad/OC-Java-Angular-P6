package com.openclassrooms.mdd.repository;

import com.openclassrooms.mdd.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    /**
     * Find all subscriptions for a specific user.
     *
     * @param userEmail the email of the user
     * @return list of subscriptions for the user
     */
    List<Subscription> findByUserEmail(String userEmail);

    /**
     * Check if a user is subscribed to a specific topic.
     *
     * @param userEmail  the email of the user
     * @param topicId the ID of the topic
     * @return true if the subscription exists, false otherwise
     */
    boolean existsByUserEmailAndTopicId(String userEmail, Long topicId);

    /**
     * Delete a subscription for a specific user and topic.
     *
     * @param userEmail  the email of the user
     * @param topicId the ID of the topic
     */
    void deleteByUserEmailAndTopicId(String userEmail, Long topicId);
}
