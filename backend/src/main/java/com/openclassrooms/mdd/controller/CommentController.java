package com.openclassrooms.mdd.controller;

import com.openclassrooms.mdd.dto.request.CreateCommentRequest;
import com.openclassrooms.mdd.dto.response.CommentResponse;
import com.openclassrooms.mdd.service.comment.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CreateCommentRequest request,
            Principal principal) {
        CommentResponse response = commentService.createComment(principal.getName(), postId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
