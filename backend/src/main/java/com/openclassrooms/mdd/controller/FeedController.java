package com.openclassrooms.mdd.controller;

import com.openclassrooms.mdd.dto.response.PostResponse;
import com.openclassrooms.mdd.service.feed.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getUserFeed(
            @RequestParam(name = "sort", defaultValue = "desc") String sortOrder,
            Principal principal) {
        List<PostResponse> feed = feedService.getUserFeed(principal.getName(), sortOrder);
        return ResponseEntity.ok(feed);
    }
}
