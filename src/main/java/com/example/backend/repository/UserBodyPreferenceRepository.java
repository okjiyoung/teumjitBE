package com.example.backend.repository;

import com.example.backend.domain.User;
import com.example.backend.domain.UserBodyPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBodyPreferenceRepository
        extends JpaRepository<UserBodyPreference, Long> {

    List<UserBodyPreference> findByUser(User user);

    List<UserBodyPreference> findByUserAndPreferenceType(
            User user,
            String preferenceType
    );

    List<UserBodyPreference> findByUserAndBodyArea(
            User user,
            String bodyArea
    );

    boolean existsByUserAndBodyArea(
            User user,
            String bodyArea
    );
}