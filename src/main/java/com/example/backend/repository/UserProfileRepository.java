package com.example.backend.repository;

import com.example.backend.domain.User;
import com.example.backend.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository
        extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByUser(User user);

    Optional<UserProfile> findByUserId(Long userId);

    Optional<UserProfile> findByEmail(String email);
}