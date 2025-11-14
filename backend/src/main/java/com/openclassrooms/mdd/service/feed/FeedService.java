package com.openclassrooms.mdd.service.feed;

import com.openclassrooms.mdd.dto.response.PostResponse;

import java.util.List;

public interface FeedService {

    /**
     * Get the personalized feed for a user.
     * Returns posts from topics the user is subscribed to, sorted by creation date.
     *
     * @param userEmail the email of the user
     * @param sortOrder the sort order: "asc" (oldest first) or "desc" (most recent first)
     * @return list of posts from subscribed topics, sorted by date
     */
    List<PostResponse> getUserFeed(String userEmail, String sortOrder);
}
