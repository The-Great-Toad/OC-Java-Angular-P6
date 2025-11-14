package com.openclassrooms.mdd.mapper;

import org.springframework.stereotype.Component;

import com.openclassrooms.mdd.dto.response.CommentResponse;
import com.openclassrooms.mdd.model.Comment;

@Component
public class CommentMapper {

    /**
     * Maps a Comment entity to a CommentResponse DTO.
     *
     * @param comment the comment entity
     * @return the comment response DTO
     */
    public CommentResponse mapToResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getAuthor().getName(),
                comment.getCreatedAt()
        );
    }
}
