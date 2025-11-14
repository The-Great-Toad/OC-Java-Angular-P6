package com.openclassrooms.mdd.repository;

import com.openclassrooms.mdd.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Find all posts by topic IDs, ordered by creation date (most recent first).
     *
     * @param topicIds list of topic IDs
     * @return list of posts ordered by creation date descending
     */
    List<Post> findByTopicIdInOrderByCreatedAtDesc(List<Long> topicIds);

    /**
     * Find all posts by topic IDs, ordered by creation date (oldest first).
     *
     * @param topicIds list of topic IDs
     * @return list of posts ordered by creation date ascending
     */
    List<Post> findByTopicIdInOrderByCreatedAtAsc(List<Long> topicIds);
}
