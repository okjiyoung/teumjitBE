
package com.example.backend.service;

import com.example.backend.domain.Stretching;
import com.example.backend.domain.StretchingDifficulty;
import com.example.backend.dto.StretchingDifficultyRequest;
import com.example.backend.dto.StretchingDifficultyResponse;
import com.example.backend.repository.StretchingDifficultyRepository;
import com.example.backend.repository.StretchingRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class StretchingDifficultyService {

    private final StretchingDifficultyRepository difficultyRepository;
    private final StretchingRepository stretchingRepository;

    public StretchingDifficultyService(
            StretchingDifficultyRepository difficultyRepository,
            StretchingRepository stretchingRepository) {

        this.difficultyRepository = difficultyRepository;
        this.stretchingRepository = stretchingRepository;
    }

    // Entity를 Response DTO로 변환
    private StretchingDifficultyResponse toResponse(
            StretchingDifficulty difficulty) {

        return new StretchingDifficultyResponse(
                difficulty.getId(),
                difficulty.getStretchingId(),
                difficulty.getDifficultyType(),
                difficulty.getDifficulty()
        );
    }

    // 모든 difficulty 조회
    public List<StretchingDifficultyResponse> getAll() {

        return difficultyRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // difficulty ID로 조회
    public StretchingDifficultyResponse getById(Long id) {

        StretchingDifficulty difficulty =
                difficultyRepository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "해당 difficulty가 존재하지 않습니다. id=" + id
                                )
                        );

        return toResponse(difficulty);
    }

    // 특정 stretching의 difficulty 조회
    public List<StretchingDifficultyResponse> getByStretchingId(
            Long stretchingId) {

        return difficultyRepository
                .findByStretchingId(stretchingId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // difficulty 등록
    public StretchingDifficultyResponse create(
            StretchingDifficultyRequest request) {

        StretchingDifficulty difficulty =
                new StretchingDifficulty(
                        request.getStretchingId(),
                        request.getDifficultyType(),
                        request.getDifficulty()
                );

        return toResponse(
                difficultyRepository.save(difficulty)
        );
    }

    // difficulty 수정
    public StretchingDifficultyResponse update(
            Long id,
            StretchingDifficultyRequest request) {

        StretchingDifficulty difficulty =
                difficultyRepository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "해당 difficulty가 존재하지 않습니다. id=" + id
                                )
                        );

        difficulty.setStretchingId(request.getStretchingId());
        difficulty.setDifficultyType(request.getDifficultyType());
        difficulty.setDifficulty(request.getDifficulty());

        return toResponse(
                difficultyRepository.save(difficulty)
        );
    }

    // difficulty 삭제
    public void delete(Long id) {

        if (!difficultyRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "해당 difficulty가 존재하지 않습니다. id=" + id
            );
        }

        difficultyRepository.deleteById(id);
    }

    // 여러 difficulty 조건으로 스트레칭 조회
    public List<Stretching> getStretchingsByDifficulties(
            List<String> difficulties) {

        // 조건이 없으면 전체 조회
        if (difficulties == null || difficulties.isEmpty()) {
            return stretchingRepository.findAll();
        }

        // 조건을 종류별로 그룹화
        Map<String, Set<String>> selectedConditions = new HashMap<>();

        for (String difficulty : difficulties) {

            if (difficulty == null) {
                throw new IllegalArgumentException(
                        "difficulty 조건은 null일 수 없습니다."
                );
            }

            String[] condition = difficulty.split(":", 2);

            if (condition.length != 2
                    || condition[0].isBlank()
                    || condition[1].isBlank()) {

                throw new IllegalArgumentException(
                        "difficulty 형식이 잘못되었습니다. "
                        + "예: CONSTRAINT:ONE_HAND"
                );
            }

            String type = condition[0].trim().toUpperCase();
            String value = condition[1].trim().toUpperCase();

            selectedConditions
                    .computeIfAbsent(type, key -> new HashSet<>())
                    .add(value);
        }

        // 스트레칭별 태그 조회
        List<StretchingDifficulty> allDifficulties =
                difficultyRepository.findAll();

        // stretchingId -> difficultyType -> difficulty 값들
        Map<Long, Map<String, Set<String>>> tagsByStretching =
                new HashMap<>();

        for (StretchingDifficulty tag : allDifficulties) {

            Long stretchingId = tag.getStretchingId();

            String type = tag.getDifficultyType()
                    .trim().toUpperCase();

            String value = tag.getDifficulty()
                    .trim().toUpperCase();

            tagsByStretching
                    .computeIfAbsent(stretchingId, key -> new HashMap<>())
                    .computeIfAbsent(type, key -> new HashSet<>())
                    .add(value);
        }

        // 조건에 맞는 스트레칭만 반환
        return stretchingRepository.findAll()
                .stream()
                .filter(stretching -> {

                    Map<String, Set<String>> stretchingTags =
                            tagsByStretching.getOrDefault(
                                    stretching.getId(),
                                    Collections.emptyMap()
                            );

                    for (Map.Entry<String, Set<String>> entry
                            : selectedConditions.entrySet()) {

                        String type = entry.getKey();
                        Set<String> selectedValues = entry.getValue();

                        // ANY가 있으면 해당 종류의 필터를 생략
                        if (selectedValues.contains("ANY")) {
                            continue;
                        }

                        Set<String> actualValues =
                                stretchingTags.getOrDefault(
                                        type,
                                        Collections.emptySet()
                                );

                        // 제약 조건: 선택한 조건을 모두 만족
                        if ("CONSTRAINT".equals(type)) {

                            if (!actualValues.containsAll(selectedValues)) {
                                return false;
                            }

                        // 자세: 선택한 자세 중 하나 이상 만족
                        } else if ("POSTURE".equals(type)) {

                            boolean matchesAnyPosture =
                                    selectedValues.stream()
                                            .anyMatch(actualValues::contains);

                            if (!matchesAnyPosture) {
                                return false;
                            }

                        // 그 외 종류: 선택한 값을 모두 만족
                        } else {

                            if (!actualValues.containsAll(selectedValues)) {
                                return false;
                            }
                        }
                    }

                    return true;
                })
                .collect(Collectors.toList());
    }
}
