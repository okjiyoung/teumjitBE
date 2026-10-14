package com.example.backend.dto;

import com.example.backend.domain.User;

public class UserResponse {

    private Long id;
    private String userId;

    public UserResponse(Long id, String userId) {
        this.id = id;
        this.userId = userId;
    }

    public static UserResponse from(User user) {
        return new UserResponse(
            user.getId(),
            user.getUserId()
        );
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }
}
