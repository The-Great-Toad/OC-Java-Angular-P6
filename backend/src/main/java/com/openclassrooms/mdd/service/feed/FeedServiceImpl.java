package com.openclassrooms.mdd.service.feed;

import com.openclassrooms.mdd.dto.response.CommentResponse;
import com.openclassrooms.mdd.dto.response.PostResponse;
import com.openclassrooms.mdd.dto.response.SubscriptionResponse;
import com.openclassrooms.mdd.mapper.PostMapper;
import com.openclassrooms.mdd.model.Post;
import com.openclassrooms.mdd.repository.PostRepository;
import com.openclassrooms.mdd.service.comment.CommentService;
import com.openclassrooms.mdd.service.subscription.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    public static final String ASC = "asc";
    private final SubscriptionService subscriptionService;
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final CommentService commentService;

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getUserFeed(String userEmail, String sortOrder) {
        log.debug("Fetching feed for user {} with sort order: {}", userEmail, sortOrder);

        List<SubscriptionResponse> subscriptions = subscriptionService.getUserSubscriptions(userEmail);

        if (subscriptions.isEmpty()) {
            log.debug("User {} has no subscriptions, returning empty feed", userEmail);
            return Collections.emptyList();
        }

        List<Long> topicIds = subscriptions.stream()
                .map(SubscriptionResponse::topicId)
                .toList();

        List<Post> posts;
        if (ASC.equalsIgnoreCase(sortOrder)) {
            posts = postRepository.findByTopicIdInOrderByCreatedAtAsc(topicIds);
            log.debug("Fetched {} posts sorted by date ascending", posts.size());
        } else {
            posts = postRepository.findByTopicIdInOrderByCreatedAtDesc(topicIds);
            log.debug("Fetched {} posts sorted by date descending", posts.size());
        }

        return posts.stream()
                .map(post -> postMapper.mapToResponse(post, null))
                .toList();
    }
}
