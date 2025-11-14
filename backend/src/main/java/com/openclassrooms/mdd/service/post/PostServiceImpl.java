package com.openclassrooms.mdd.service.post;

import com.openclassrooms.mdd.dto.request.CreatePostRequest;
import com.openclassrooms.mdd.dto.response.PostResponse;
import com.openclassrooms.mdd.exception.ResourceNotFoundException;
import com.openclassrooms.mdd.mapper.PostMapper;
import com.openclassrooms.mdd.model.Post;
import com.openclassrooms.mdd.model.Topic;
import com.openclassrooms.mdd.model.User;
import com.openclassrooms.mdd.repository.PostRepository;
import com.openclassrooms.mdd.repository.TopicRepository;
import com.openclassrooms.mdd.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final PostMapper postMapper;

    /** {@inheritDoc} */
    @Override
    @Transactional
    public PostResponse createPost(String userEmail, CreatePostRequest request) {
        log.debug("Creating new post for user email: {} with topic ID: {}", userEmail, request.topicId());

        User author = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Topic topic = topicRepository.findById(request.topicId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found"));

        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .author(author)
                .topic(topic)
                .build();

        Post savedPost = postRepository.save(post);
        log.info("Post created successfully with ID: {}", savedPost.getId());

        return postMapper.mapToResponse(savedPost);
    }

    /** {@inheritDoc} */
    @Override
    public PostResponse getPostById(Long id) {
        log.debug("Fetching post with ID: {}", id);

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with ID " + id + " not found"));

        return postMapper.mapToResponse(post);
    }
}
