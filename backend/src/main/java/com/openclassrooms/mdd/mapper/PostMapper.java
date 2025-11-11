package com.openclassrooms.mdd.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.openclassrooms.mdd.dto.TopicDto;
import com.openclassrooms.mdd.dto.response.CommentResponse;
import com.openclassrooms.mdd.dto.response.PostResponse;
import com.openclassrooms.mdd.model.Post;
import com.openclassrooms.mdd.service.comment.CommentService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final CommentService commentService;

    /**
     * Maps a Post entity to a PostResponse DTO.
     * Includes all comments associated with the post.
     *
     * @param post the post entity to map
     * @return the mapped PostResponse with comments
     */
    public PostResponse mapToResponse(Post post) {
        TopicDto topicDto = new TopicDto(
                post.getTopic().getId(),
                post.getTopic().getName(),
                post.getTopic().getDescription()
        );

        List<CommentResponse> comments = commentService.getCommentsByPost(post.getId());

        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getName(),
                topicDto,
                post.getCreatedAt(),
                comments
        );
    }
}
