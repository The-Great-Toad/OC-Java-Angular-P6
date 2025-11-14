package com.openclassrooms.mdd.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.openclassrooms.mdd.dto.request.LoginRequest;
import com.openclassrooms.mdd.dto.request.RegisterRequest;
import com.openclassrooms.mdd.dto.request.UpdateProfileRequest;
import com.openclassrooms.mdd.dto.response.LoginResponse;
import com.openclassrooms.mdd.dto.response.UserProfileResponse;

public interface UserService extends UserDetailsService {

    /**
     * Registers a new user in the system.
     *
     * @param request the registration data (email, username, password)
     * @return the authentication token for the newly registered user
     */
    LoginResponse register(RegisterRequest request);

    /**
     * Authenticates a user with email/username and password.
     *
     * @param request the login credentials (identifier and password)
     * @return the authentication token for the logged-in user
     */
    LoginResponse login(LoginRequest request);

    /**
     * Retrieves the profile of the authenticated user.
     *
     * @param userEmail the email of the authenticated user (extracted from JWT)
     * @return the user's profile information including subscriptions
     */
    UserProfileResponse getUserProfile(String userEmail);

    /**
     * Updates the profile of the authenticated user.
     *
     * @param userEmail the email of the authenticated user (extracted from JWT)
     * @param request the update data (email, username, password - all optional)
     * @return the updated user profile
     */
    UserProfileResponse updateProfile(String userEmail, UpdateProfileRequest request);
}
