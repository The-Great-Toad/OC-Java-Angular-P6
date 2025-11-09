package com.openclassrooms.mdd.controller;

import com.openclassrooms.mdd.dto.request.CreatePostRequest;
import com.openclassrooms.mdd.dto.response.PostResponse;
import com.openclassrooms.mdd.service.post.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            Principal principal,
            @Valid @RequestBody CreatePostRequest request) {
        PostResponse response = postService.createPost(principal.getName(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        PostResponse response = postService.getPostById(id);
        return ResponseEntity.ok(response);
    }
}
