package com.openclassrooms.mdd.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.openclassrooms.mdd.dto.TopicDto;
import com.openclassrooms.mdd.dto.response.CommentResponse;
import com.openclassrooms.mdd.dto.response.PostResponse;
import com.openclassrooms.mdd.model.Post;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PostMapper {

    /**
     * Maps a Post entity to a PostResponse DTO.
     * Includes all comments associated with the post.
     *
     * @param post the post entity to map
     * @return the mapped PostResponse with comments
     */
    public PostResponse mapToResponse(Post post, List<CommentResponse> comments) {
        TopicDto topicDto = new TopicDto(
                post.getTopic().getId(),
                post.getTopic().getName(),
                post.getTopic().getDescription());

        PostResponse postResponse = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor().getName())
                .topic(topicDto)
                .createdAt(post.getCreatedAt())
                .build();

        if (Objects.nonNull(comments)) {
            postResponse.setComments(comments);
        }

        return postResponse;
    }
}
