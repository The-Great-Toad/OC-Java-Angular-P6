package com.openclassrooms.mdd.service.comment;

import com.openclassrooms.mdd.dto.request.CreateCommentRequest;
import com.openclassrooms.mdd.dto.response.CommentResponse;

import java.util.List;

public interface CommentService {

    /**
     * Create a new comment on a post.
     * The author and creation date are set automatically.
     *
     * @param userEmail  the email of the authenticated user creating the comment
     * @param postId  the ID of the post to comment on
     * @param request the comment creation request containing the content
     * @return the created comment as a CommentResponse
     */
    CommentResponse createComment(String userEmail, Long postId, CreateCommentRequest request);

    /**
     * Get all comments for a specific post, ordered chronologically (oldest first).
     *
     * @param postId the ID of the post
     * @return list of comments for the post
     */
    List<CommentResponse> getCommentsByPost(Long postId);
}
