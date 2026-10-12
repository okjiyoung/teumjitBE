package com.example.backend.dto;

import com.example.backend.domain.Stretching;
import com.example.backend.domain.UserFavoriteStretching;
import com.example.backend.domain.UserFavoriteStretchingItem;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "저장된 스트레칭 루틴 응답")
public class UserFavoriteStretchingResponse {

    @Schema(description = "즐겨찾기 루틴 ID", example = "101")
    private Long id;

    @Schema(description = "루틴을 저장한 사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "루틴 제목", example = "목 · 어깨 · 다리")
    private String title;

    @Schema(description = "루틴 저장 시각")
    private LocalDateTime createdAt;

    @ArraySchema(
        arraySchema = @Schema(description = "실행 순서대로 정렬된 스트레칭 목록"),
        schema = @Schema(implementation = StretchingItemResponse.class)
    )
    private List<StretchingItemResponse> stretchings;

    public UserFavoriteStretchingResponse() {
    }

    public UserFavoriteStretchingResponse(
            Long id,
            Long userId,
            String title,
            LocalDateTime createdAt,
            List<StretchingItemResponse> stretchings
    ) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.createdAt = createdAt;
        this.stretchings = stretchings;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<StretchingItemResponse> getStretchings() {
        return stretchings;
    }

    @Schema(description = "루틴에 포함된 개별 스트레칭 정보")
    public static class StretchingItemResponse {

        @Schema(description = "스트레칭 ID", example = "5")
        private Long id;

        @Schema(description = "스트레칭 이름", example = "턱 힘 빼기")
        private String name;

        @Schema(description = "스트레칭 부위", example = "얼굴")
        private String area;

        @Schema(description = "스트레칭 설명", example = "입술은 가볍게 닫고 위아래 치아는 떨어뜨립니다. 혀를 입천장에 편안하게 둔 상태로 천천히 호흡합니다.")
        private String description;

        @Schema(description = "난이도 (0: Easy, 1: Middle, 2: Hard)", example = "0")
        private int level;

        @Schema(description = "소요 시간(초)", example = "30")
        private int durationSeconds;

        @Schema(description = "루틴 내 실행 순서", example = "1")
        private int sequenceOrder;

        public StretchingItemResponse() {
        }

        public StretchingItemResponse(
                Long id,
                String name,
                String area,
                String description,
                int level,
                int durationSeconds,
                int sequenceOrder
        ) {
            this.id = id;
            this.name = name;
            this.area = area;
            this.description = description;
            this.level = level;
            this.durationSeconds = durationSeconds;
            this.sequenceOrder = sequenceOrder;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getArea() {
            return area;
        }

        public String getDescription() {
            return description;
        }

        public int getLevel() {
            return level;
        }

        public int getDurationSeconds() {
            return durationSeconds;
        }

        public int getSequenceOrder() {
            return sequenceOrder;
        }
    }
}