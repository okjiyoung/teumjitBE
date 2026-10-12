package com.example.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "사용자 위치 데이터 등록 요청")
public class LocationDataRequest {

    @Schema(
        description = "위치 데이터를 등록할 사용자의 ID",
        example = "1"
    )
    private Long userId;

    @Schema(
        description = "위도",
        example = "37.5665"
    )
    private double latitude;

    @Schema(
        description = "경도",
        example = "126.9780"
    )
    private double longitude;

    @Schema(
        description = "위치 데이터가 측정된 날짜와 시간",
        example = "2026-10-12T15:30:00"
    )
    private LocalDateTime measuredAt;

    public LocationDataRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getMeasuredAt() {
        return measuredAt;
    }

    public void setMeasuredAt(LocalDateTime measuredAt) {
        this.measuredAt = measuredAt;
    }
}