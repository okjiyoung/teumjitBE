package com.example.backend.service;

import com.example.backend.domain.HealthData;
import com.example.backend.domain.User;
import com.example.backend.dto.HealthDataRequest;
import com.example.backend.repository.HealthDataRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.Comparator;

import com.example.backend.dto.InactiveResponse; // 부동자세 판단 가져오기

@Service
public class HealthDataService {

    private final HealthDataRepository healthDataRepository;
    private final UserRepository userRepository;

    public HealthDataService(
            HealthDataRepository healthDataRepository,
            UserRepository userRepository
    ) {
        this.healthDataRepository = healthDataRepository;
        this.userRepository = userRepository;
    }

    // 건강 데이터 등록
    public HealthData createHealthData(
            HealthDataRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("회원을 찾을 수 없습니다.")
                );

        HealthData healthData = new HealthData(
                user,
                request.getDataType(),
                request.getValue(),
                request.getUnit(),
                request.getMeasuredAt(),
                request.getSource()
        );

        return healthDataRepository.save(healthData);
    }

    // 전체 건강 데이터 조회
    public List<HealthData> getAllHealthData() {

        return healthDataRepository.findAll();
    }

    // 특정 사용자의 건강 데이터 조회
    public List<HealthData> getHealthDataByUser(
            Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("회원을 찾을 수 없습니다.")
                );

        return healthDataRepository.findByUser(user);
    }

    // 건강 데이터 수정
    public HealthData updateHealthData(
            Long id,
            HealthDataRequest request) {

        HealthData healthData =
                healthDataRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "건강 데이터를 찾을 수 없습니다."
                                )
                        );

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("회원을 찾을 수 없습니다.")
                );

        healthData.setUser(user);
        healthData.setDataType(request.getDataType());
        healthData.setValue(request.getValue());
        healthData.setUnit(request.getUnit());
        healthData.setMeasuredAt(request.getMeasuredAt());
        healthData.setSource(request.getSource());

        return healthDataRepository.save(healthData);
    }

    // 건강 데이터 삭제
    public void deleteHealthData(Long id) {

        HealthData healthData =
                healthDataRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "건강 데이터를 찾을 수 없습니다."
                                )
                        );

        healthDataRepository.delete(healthData);
    }

    // 부동 상태 확인
    public InactiveResponse checkInactive(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        List<HealthData> intervalRecords =
                healthDataRepository.findByUser(user)
                        .stream()
                        .filter(data ->
                                "STEPS_INTERVAL".equals(data.getDataType()))
                        .filter(data -> data.getMeasuredAt() != null)
                        .filter(data -> data.getValue() > 0)
                        .sorted(Comparator.comparing(HealthData::getMeasuredAt))
                        .toList();

        if (intervalRecords.isEmpty()) {
                // 움직임 기록이 없다는 이유만으로 부동 상태라고 단정하지 않습니다.
                return new InactiveResponse(false, 0);
        }

        LocalDateTime lastMovementTime =
                intervalRecords.get(intervalRecords.size() - 1)
                        .getMeasuredAt();

        long inactiveMinutes = Math.max(
                0,
                Duration.between(
                        lastMovementTime,
                        LocalDateTime.now()
                ).toMinutes()
        );

        boolean inactive = inactiveMinutes >= 50;

        return new InactiveResponse(inactive, inactiveMinutes);
        }
}