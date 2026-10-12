package com.example.backend.service;

import com.example.backend.domain.User;
import com.example.backend.domain.UserProfile;
import com.example.backend.dto.UserProfileRequest;
import com.example.backend.repository.UserProfileRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    public UserProfileService(
            UserProfileRepository userProfileRepository,
            UserRepository userRepository
    ) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }

    public UserProfile createProfile(UserProfileRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("회원을 찾을 수 없습니다.")
                );

        UserProfile profile = new UserProfile(
                user,
                request.getEmail(),
                request.getNickname(),
                request.getHomeAddress(),
                request.getHomeLatitude(),
                request.getHomeLongitude()
        );

        return userProfileRepository.save(profile);
    }

    public UserProfile getProfile(Long userId) {

        return userProfileRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException("사용자 프로필을 찾을 수 없습니다.")
                );
    }

    public UserProfile updateProfile(
            Long userId,
            UserProfileRequest request
    ) {

        UserProfile profile =
                userProfileRepository.findByUserId(userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "사용자 프로필을 찾을 수 없습니다."
                                )
                        );

        profile.setEmail(request.getEmail());
        profile.setNickname(request.getNickname());
        profile.setHomeAddress(request.getHomeAddress());
        profile.setHomeLatitude(request.getHomeLatitude());
        profile.setHomeLongitude(request.getHomeLongitude());

        return userProfileRepository.save(profile);
    }
}