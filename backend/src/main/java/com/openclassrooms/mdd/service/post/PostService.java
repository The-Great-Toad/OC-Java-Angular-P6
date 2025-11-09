package com.openclassrooms.mdd.service.post;

import com.openclassrooms.mdd.dto.request.CreatePostRequest;
import com.openclassrooms.mdd.dto.response.PostResponse;

public interface PostService {

    /**
     * Creates a new post.
     * The author is automatically set from the authenticated user.
     * The creation date is automatically set.
     *
     * @param userEmail the email of the authenticated user (author)
     * @param request the post creation data (topicId, title, content)
     * @return the created post
     */
    PostResponse createPost(String userEmail, CreatePostRequest request);

    /**
     * Retrieves a post by its ID with all details.
     *
     * @param id the ID of the post to retrieve
     * @return the post details
     */
    PostResponse getPostById(Long id);
}
