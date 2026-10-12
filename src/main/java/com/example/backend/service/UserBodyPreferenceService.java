package com.example.backend.service;

import com.example.backend.domain.User;
import com.example.backend.domain.UserBodyPreference;
import com.example.backend.dto.UserBodyPreferenceRequest;
import com.example.backend.repository.UserBodyPreferenceRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBodyPreferenceService {

    private final UserBodyPreferenceRepository preferenceRepository;
    private final UserRepository userRepository;

    public UserBodyPreferenceService(
            UserBodyPreferenceRepository preferenceRepository,
            UserRepository userRepository
    ) {
        this.preferenceRepository = preferenceRepository;
        this.userRepository = userRepository;
    }

    public UserBodyPreference createPreference(
        UserBodyPreferenceRequest request
) {

    User user = userRepository.findById(request.getUserId())
            .orElseThrow(() ->
                    new RuntimeException("회원을 찾을 수 없습니다.")
            );

    // 이미 해당 신체 부위가 등록되어 있는지 확인
    boolean alreadyExists =
            preferenceRepository.existsByUserAndBodyArea(
                    user,
                    request.getBodyArea()
            );

    if (alreadyExists) {
        throw new RuntimeException(
                "이미 등록된 신체 부위입니다. " +
                "하나의 신체 부위는 AVOID 또는 PREFER 중 하나만 선택할 수 있습니다."
        );
    }

    UserBodyPreference preference =
            new UserBodyPreference(
                    user,
                    request.getPreferenceType(),
                    request.getBodyArea()
            );

    return preferenceRepository.save(preference);
}

    public List<UserBodyPreference> getPreferences(
            Long userId
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("회원을 찾을 수 없습니다.")
                );

        return preferenceRepository.findByUser(user);
    }

    public List<UserBodyPreference> getPreferencesByType(
            Long userId,
            String preferenceType
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("회원을 찾을 수 없습니다.")
                );

        return preferenceRepository
                .findByUserAndPreferenceType(
                        user,
                        preferenceType
                );
    }

    public void deletePreference(Long id) {

        if (!preferenceRepository.existsById(id)) {
            throw new RuntimeException(
                    "해당 신체 부위 설정을 찾을 수 없습니다."
            );
        }

        preferenceRepository.deleteById(id);
    }
}