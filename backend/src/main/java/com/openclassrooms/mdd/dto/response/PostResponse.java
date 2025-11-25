package com.openclassrooms.mdd.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.openclassrooms.mdd.dto.TopicDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private String author;
    private TopicDto topic;
    private LocalDateTime createdAt;
    private List<CommentResponse> comments;
}
