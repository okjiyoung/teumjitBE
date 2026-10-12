package com.example.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "스트레칭 등록 및 수정 요청")
public class StretchingRequest {

    @Schema(
        description = "스트레칭 이름",
        example = "턱 힘 빼기"
    )
    private String name;

    @Schema(
        description = "스트레칭 난이도",
        example = "0"
    )
    private int level;

    @Schema(
        description = "스트레칭 동작 부위",
        example = "얼굴"
    )
    private String area;

    @Schema(
        description = "스트레칭 동작 방법에 대한 설명",
        example = "입술은 가볍게 닫고 위아래 치아는 떨어뜨립니다. 혀를 입천장에 편안하게 둔 상태로 천천히 호흡합니다."
    )
    private String description;

    @Schema(
        description = "스트레칭 소요 시간(초)",
        example = "30"
    )
    private int durationSeconds;

    public StretchingRequest() {
    }

    // 이름
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 난이도
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    // 동작 부위
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    // 설명
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // 소요 시간
    public int getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
    }
}