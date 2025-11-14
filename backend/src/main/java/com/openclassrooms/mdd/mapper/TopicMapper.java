package com.openclassrooms.mdd.mapper;

import org.springframework.stereotype.Component;

import com.openclassrooms.mdd.dto.TopicDto;
import com.openclassrooms.mdd.dto.response.SubscriptionResponse;
import com.openclassrooms.mdd.model.Topic;

@Component
public class TopicMapper {
    
    /**
     * Maps a Topic entity to a TopicDto.
     *
     * @param topic the topic entity to map
     * @return the mapped TopicDto
     */
    public TopicDto mapToDto(Topic topic) {
        return new TopicDto(topic.getId(), topic.getName(), topic.getDescription());
    }

    /**
     * Maps a SubscriptionResponse to a TopicDto.
     *
     * @param subscription the subscription response to map
     * @return the mapped TopicDto
     */
    public TopicDto mapToDto(SubscriptionResponse subscription) {
        return new TopicDto(subscription.topicId(), subscription.topicName(), subscription.topicDescription());
    }
}
