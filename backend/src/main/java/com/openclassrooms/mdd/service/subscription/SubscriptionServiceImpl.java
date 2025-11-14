package com.openclassrooms.mdd.service.subscription;

import com.openclassrooms.mdd.dto.response.SubscriptionResponse;
import com.openclassrooms.mdd.exception.ResourceNotFoundException;
import com.openclassrooms.mdd.mapper.SubscriptionMapper;
import com.openclassrooms.mdd.model.Subscription;
import com.openclassrooms.mdd.model.Topic;
import com.openclassrooms.mdd.model.User;
import com.openclassrooms.mdd.repository.SubscriptionRepository;
import com.openclassrooms.mdd.repository.TopicRepository;
import com.openclassrooms.mdd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    @Transactional
    public SubscriptionResponse subscribe(String userEmail, Long topicId) {
        log.info("User {} subscribing to topic {}", userEmail, topicId);

        if (isSubscribed(userEmail, topicId)) {
            log.debug("User {} is already subscribed to topic {}", userEmail, topicId);
            /* Return existing subscription instead of throwing exception - idempotent */
            Subscription existing = subscriptionRepository.findByUserEmail(userEmail).stream()
                    .filter(s -> s.getTopic().getId().equals(topicId))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Subscription should exist but was not found"));
            return subscriptionMapper.mapToResponse(existing);
        }

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with id: " + topicId));

        Subscription subscription = Subscription.builder()
                .user(user)
                .topic(topic)
                .build();

        Subscription saved = subscriptionRepository.save(subscription);
        log.info("User {} successfully subscribed to topic {}", userEmail, topicId);

        return subscriptionMapper.mapToResponse(saved);
    }

    @Override
    @Transactional
    public void unsubscribe(String userEmail, Long topicId) {
        log.info("User {} unsubscribing from topic {}", userEmail, topicId);

        if (!isSubscribed(userEmail, topicId)) {
            throw new ResourceNotFoundException("Subscription not found for user " + userEmail + " and topic " + topicId);
        }

        subscriptionRepository.deleteByUserEmailAndTopicId(userEmail, topicId);
        log.info("User {} successfully unsubscribed from topic {}", userEmail, topicId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionResponse> getUserSubscriptions(String userEmail) {
        log.debug("Fetching subscriptions for user {}", userEmail);

        List<Subscription> subscriptions = subscriptionRepository.findByUserEmail(userEmail);

        return subscriptions.stream()
                .map(subscriptionMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Check if a user is subscribed to a specific topic.
     *
     * @param userEmail  the email of the user
     * @param topicId the ID of the topic
     * @return true if subscribed, false otherwise
     */
    @Transactional(readOnly = true)
    public boolean isSubscribed(String userEmail, Long topicId) {
        return subscriptionRepository.existsByUserEmailAndTopicId(userEmail, topicId);
    }
}
