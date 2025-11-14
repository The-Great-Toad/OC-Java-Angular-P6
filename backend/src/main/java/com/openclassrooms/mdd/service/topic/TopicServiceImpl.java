package com.openclassrooms.mdd.service.topic;

import com.openclassrooms.mdd.dto.TopicDto;
import com.openclassrooms.mdd.mapper.TopicMapper;
import com.openclassrooms.mdd.repository.TopicRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    @Override
    public List<TopicDto> getAllTopics() {
        log.debug("Fetching all topics");
        return topicRepository.findAll().stream()
                .map(topicMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
