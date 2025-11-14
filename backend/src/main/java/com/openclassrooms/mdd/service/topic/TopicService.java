package com.openclassrooms.mdd.service.topic;

import com.openclassrooms.mdd.dto.TopicDto;

import java.util.List;

public interface TopicService {

    /** Retrieves all topics. */
    List<TopicDto> getAllTopics();
}
