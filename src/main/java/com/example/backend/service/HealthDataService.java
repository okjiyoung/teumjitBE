package com.example.backend.service;

import com.example.backend.domain.HealthData;
import com.example.backend.domain.User;
import com.example.backend.dto.HealthDataRequest;
import com.example.backend.repository.HealthDataRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.backend.dto.InactiveResponse; // 부동자세 판단 가져오기
import java.time.LocalDateTime;
import java.time.Duration;


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

    // 1. 사용자 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("회원을 찾을 수 없습니다.")
                );

    // 2. 해당 사용자의 건강 데이터 가져오기
        List<HealthData> healthDataList =
               healthDataRepository.findByUser(user);

    // 3. 데이터가 없으면 판단할 수 없음
        if (healthDataList.isEmpty()) {
            return new InactiveResponse(false, 0);
        }

    // 4. STEPS 데이터만 사용
        List<HealthData> stepDataList =
            healthDataList.stream()
                .filter(data ->
                        "STEPS".equals(data.getDataType())
                )
                .filter(data ->
                        data.getMeasuredAt() != null // NULL 데이터는 부동 판단에서 제외
                )
                .sorted((data1, data2) ->
                        data1.getMeasuredAt()
                                .compareTo(data2.getMeasuredAt())
                )
                .toList();

    // 5. STEPS 데이터가 없으면 판단할 수 없음
        if (stepDataList.isEmpty()) {
            return new InactiveResponse(false, 0);
        }

    // 6. 가장 최근의 걸음 수
        double latestSteps =
                stepDataList.get(
                        stepDataList.size() - 1
                ).getValue();

    // 7. 마지막으로 걸음 수가 증가한 시간
        LocalDateTime lastMovementTime =
                stepDataList.get(0).getMeasuredAt();

    // 8. 이전 데이터와 비교
        for (int i = 1; i < stepDataList.size(); i++) {

            double previousSteps =
                    stepDataList.get(i - 1).getValue();

            double currentSteps =
                    stepDataList.get(i).getValue();

        // 걸음 수가 증가했다면
        // 해당 시점을 마지막 움직임 시간으로 설정
            if (currentSteps > previousSteps) {

                lastMovementTime =
                        stepDataList.get(i).getMeasuredAt();
            }
        }

    // 9. 현재 시간
        LocalDateTime now =
                LocalDateTime.now();

    // 10. 마지막 움직임 이후 경과 시간
        long inactiveMinutes =
                Duration.between(
                        lastMovementTime,
                        now
                ).toMinutes();

    // 11. 50분 이상 움직임이 없으면 부동 상태
        boolean inactive =
                inactiveMinutes >= 50;

        return new InactiveResponse(
                inactive,
                inactiveMinutes
        );
    }
}