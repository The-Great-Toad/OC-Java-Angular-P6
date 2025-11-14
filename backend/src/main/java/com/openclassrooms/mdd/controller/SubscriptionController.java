package com.openclassrooms.mdd.controller;

import com.openclassrooms.mdd.dto.response.SubscriptionResponse;
import com.openclassrooms.mdd.service.subscription.SubscriptionService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    public ResponseEntity<List<SubscriptionResponse>> getUserSubscriptions(Principal principal) {
        List<SubscriptionResponse> subscriptions = subscriptionService.getUserSubscriptions(principal.getName());
        return ResponseEntity.ok(subscriptions);
    }

    @PostMapping("/{topicId}/subscribe")
    public ResponseEntity<SubscriptionResponse> subscribe(
            @PathVariable Long topicId,
            Principal principal) {
        SubscriptionResponse response = subscriptionService.subscribe(principal.getName(), topicId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{topicId}/unsubscribe")
    public ResponseEntity<Void> unsubscribe(
            @PathVariable Long topicId,
            Principal principal) {
        subscriptionService.unsubscribe(principal.getName(), topicId);
        return ResponseEntity.noContent().build();
    }
}
