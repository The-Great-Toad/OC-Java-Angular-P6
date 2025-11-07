package com.openclassrooms.mdd.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.openclassrooms.mdd.dto.request.RegisterRequest;
import com.openclassrooms.mdd.model.User;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Map RegisterRequest to User entity.
     *
     * @param request the registration request containing user details
     * @return a User entity populated with the registration details
     */
    public User mapToUser(RegisterRequest request) {
        return User.builder()
            .email(request.email())
            .name(request.name())
            .password(passwordEncoder.encode(request.password()))
            .build();
    }
}
