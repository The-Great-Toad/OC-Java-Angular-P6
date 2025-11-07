package com.openclassrooms.mdd.service.user;

import com.openclassrooms.mdd.constant.ValidationMessages;
import com.openclassrooms.mdd.dto.request.LoginRequest;
import com.openclassrooms.mdd.dto.request.RegisterRequest;
import com.openclassrooms.mdd.dto.request.UpdateProfileRequest;
import com.openclassrooms.mdd.dto.response.LoginResponse;
import com.openclassrooms.mdd.dto.response.UserProfileResponse;
import com.openclassrooms.mdd.mapper.UserMapper;
import com.openclassrooms.mdd.model.User;
import com.openclassrooms.mdd.repository.UserRepository;
import com.openclassrooms.mdd.service.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /** {@inheritDoc} */
    @Override
    @Transactional
    public LoginResponse register(RegisterRequest request) {
        log.debug("Registering new user with email: {}", request.email());

        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException(ValidationMessages.EMAIL_ALREADY_EXISTS);
        }

        User user = userMapper.mapToUser(request);
        User savedUser = userRepository.save(user);
        log.info("User registered successfully with ID: {}", savedUser.getId());

        return jwtService.generateToken(savedUser.getId());
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
        log.debug("Login attempt with email: {}", request.email());

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadCredentialsException(ValidationMessages.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException(ValidationMessages.INVALID_CREDENTIALS);
        }

        log.info("User logged in successfully: {}", user.getName());

        return jwtService.generateToken(user.getId());
    }

    /** {@inheritDoc} */
    @Override
    public UserProfileResponse getUserProfile(String userEmail) {
        log.debug("Fetching profile for user email: {}", userEmail);

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException(ValidationMessages.USER_NOT_FOUND));

        // TODO: Fetch actual subscriptions when Subscription entity is implemented
        return new UserProfileResponse(
                user.getEmail(),
                user.getName(),
                Collections.emptyList()
        );
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public UserProfileResponse updateProfile(String userEmail, UpdateProfileRequest request) {
        log.debug("Updating profile for user email: {}", userEmail);

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException(ValidationMessages.USER_NOT_FOUND));

        /* Update email if provided and different */
        if (request.email() != null && !request.email().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.email())) {
                throw new IllegalArgumentException(ValidationMessages.EMAIL_ALREADY_EXISTS);
            }
            user.setEmail(request.email());
        }

        /* Update username if provided and different */
        if (request.username() != null && !request.username().equals(user.getName())) {
            user.setName(request.username());
        }

        /* Update password if provided */
        if (request.password() != null) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }

        User updatedUser = userRepository.save(user);
        log.info("Profile updated successfully for user ID: {}", updatedUser.getId());

        // TODO: Fetch actual subscriptions when Subscription entity is implemented
        return new UserProfileResponse(
                updatedUser.getEmail(),
                updatedUser.getName(),
                Collections.emptyList()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String uuid) throws UsernameNotFoundException {
       return userRepository
                .findById(UUID.fromString(uuid))
                .orElseThrow(() -> {
                    log.warn("User not found with ID: {}", uuid);
                    return new UsernameNotFoundException(ValidationMessages.USER_NOT_FOUND);
                });
    }
}
