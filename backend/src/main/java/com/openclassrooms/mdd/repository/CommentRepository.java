package com.openclassrooms.mdd.repository;

import com.openclassrooms.mdd.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Find all comments for a specific post, ordered by creation date (oldest first).
     *
     * @param postId the ID of the post
     * @return list of comments for the post, chronologically ordered
     */
    List<Comment> findByPostIdOrderByCreatedAtAsc(Long postId);
}
