package com.example.backend.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "stretching_difficulties")
public class StretchingDifficulty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    // 자동 지급 id : identifier
    private Long id;

    // 스트레칭 id : 어떤 스트레칭에 적용할 것인가?
    private Long stretchingId;

    // 어려움 구분 : 위치(Location) / 자세(Posture) / 동작 제약 정보(Movement)
    private String difficultyType;

    // 어떤 어려움을 가지는가?
    private String difficulty;

    // 기본 생성자
    public StretchingDifficulty() {
    }

    // 생성자
    public StretchingDifficulty(
            Long stretchingId,
            String difficultyType,
            String difficulty
    ) {
        this.stretchingId = stretchingId;
        this.difficultyType = difficultyType;
        this.difficulty = difficulty;
    }

    // id
    public Long getId() {
        return id;
    }

    // stretchingId
    public Long getStretchingId() {
        return stretchingId;
    }

    public void setStretchingId(Long stretchingId) {
        this.stretchingId = stretchingId;
    }

    // difficultyType
    public String getDifficultyType() {
        return difficultyType;
    }

    public void setDifficultyType(String difficultyType) {
        this.difficultyType = difficultyType;
    }

    // difficulty
    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}