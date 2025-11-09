package com.openclassrooms.mdd.mapper;

import org.springframework.stereotype.Component;

import com.openclassrooms.mdd.dto.TopicDto;
import com.openclassrooms.mdd.dto.response.PostResponse;
import com.openclassrooms.mdd.model.Post;

@Component
public class PostMapper {

    /**
     * Maps a Post entity to a PostResponse DTO.
     *
     * @param post the post entity to map
     * @return the mapped PostResponse
     */
    public PostResponse mapToResponse(Post post) {
        TopicDto topicDto = new TopicDto(
                post.getTopic().getId(),
                post.getTopic().getName(),
                post.getTopic().getDescription()
        );

        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getName(),
                topicDto,
                post.getCreatedAt()
        );
    }
}
