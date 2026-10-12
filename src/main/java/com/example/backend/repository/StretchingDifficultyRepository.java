package com.example.backend.repository;

import com.example.backend.domain.StretchingDifficulty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StretchingDifficultyRepository
        extends JpaRepository<StretchingDifficulty, Long> {

    // 특정 스트레칭의 모든 difficulty 조회
    List<StretchingDifficulty> findByStretchingId(Long stretchingId);

    // difficultyType으로 조회
    List<StretchingDifficulty> findByDifficultyType(String difficultyType);

    // difficulty로 조회
    List<StretchingDifficulty> findByDifficulty(String difficulty);

    // 특정 스트레칭 + difficultyType으로 조회
    List<StretchingDifficulty> findByStretchingIdAndDifficultyType(
            Long stretchingId,
            String difficultyType
    );

    // 특정 스트레칭 + difficulty로 조회
    List<StretchingDifficulty> findByStretchingIdAndDifficulty(
            Long stretchingId,
            String difficulty
    );

    // difficultyType + difficulty로 조회

    // difficultyType = POSTURE
    // difficulty = STANDING 으로 검색할 떼,
    // stretching_difficulties                    <- 형태로 나타나게 함
    // -----------------------------------------
    // stretchingId | difficultyType | difficulty
    // 1            | POSTURE        | STANDING
    // 3            | POSTURE        | STANDING
    List<StretchingDifficulty> findByDifficultyTypeAndDifficulty(
            String difficultyType,
            String difficulty
    );
}

// 예를 들어:

// findByStretchingId(1L)

// 의 경우, Spring Data 메서드가 자동으로 SQL을 만들어 주어, 

// SELECT *
// FROM stretching_difficulties
// WHERE stretching_id = 1;

// 의 결과를 갖게 됨.