package com.example.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "맞춤형 스트레칭 추천 요청")
public class StretchingRecommendationRequest {

    @Schema(description = "DB 사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "남은 시간(초)", example = "120")
    private int remainingTimeSeconds;

    @Schema(description = "현재 자세", example = "SITTING")
    private String posture;

    @Schema(description = "현재 장소", example = "OFFICE")
    private String location;

    @Schema(
        description = "사용자가 선택한 동작 제약 조건",
        example = "[\"ONE_HAND\", \"NO_LARGE_MOVEMENT\"]"
    )
    private List<String> constraints = new ArrayList<>();

    @Schema(description = "난이도: 0=쉬움, 1=보통, 2=어려움", example = "0")
    private int level;

    public StretchingRecommendationRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getRemainingTimeSeconds() {
        return remainingTimeSeconds;
    }

    public void setRemainingTimeSeconds(int remainingTimeSeconds) {
        this.remainingTimeSeconds = remainingTimeSeconds;
    }

    public String getPosture() {
        return posture;
    }

    public void setPosture(String posture) {
        this.posture = posture;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<String> constraints) {
        this.constraints = constraints;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
