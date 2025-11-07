package com.openclassrooms.mdd.controller;

import com.openclassrooms.mdd.dto.request.UpdateProfileRequest;
import com.openclassrooms.mdd.dto.response.UserProfileResponse;
import com.openclassrooms.mdd.service.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for user profile management.
 * Handles profile retrieval and updates for authenticated users.
 *
 * @author MDD Team
 * @version 1.0
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Retrieves the profile of the authenticated user.
     *
     * @param principal the authenticated user principal (contains UUID)
     * @return the user's profile information with HTTP 200 OK
     */
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getCurrentUserProfile(Principal principal) {
        UserProfileResponse profile = userService.getUserProfile(principal.getName());
        return ResponseEntity.ok(profile);
    }

    /**
     * Updates the profile of the authenticated user.
     * Email can now be changed freely without invalidating the JWT token
     * because the token uses the immutable UUID instead of the email.
     *
     * @param principal the authenticated user principal (contains UUID)
     * @param request the update data (email, username, password - all optional)
     * @return the updated user profile
     */
    @PutMapping("/me")
    public ResponseEntity<UserProfileResponse> updateCurrentUserProfile(
        Principal principal,
        @Valid @RequestBody UpdateProfileRequest request) {
        UserProfileResponse response = userService.updateProfile(principal.getName(), request);
        return ResponseEntity.ok(response);
    }
}
