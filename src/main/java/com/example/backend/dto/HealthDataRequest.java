package com.example.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "사용자 건강 데이터 등록 요청")
public class HealthDataRequest {

    @Schema(
        description = "가입 시 users 테이블에서 자동으로 부여된 사용자 ID",
        example = "1"
    )
    private Long userId; // users 테이블에서의 id == 가입 시 자동으로 부여된 id

    @Schema(
        description = "건강 데이터의 종류",
        example = "STEPS"
    )
    private String dataType; // 데이터 종류

    @Schema(
        description = "건강 데이터 측정값",
        example = "3500.0"
    )
    private double value; // 데이터 측정값

    @Schema(
        description = "측정값의 단위",
        example = "steps"
    )
    private String unit; // 단위

    @Schema(
        description = "건강 데이터가 측정된 날짜와 시간",
        example = "2026-09-28T14:30:00"
    )
    private LocalDateTime measuredAt; // 측정된 시간

    @Schema(
        description = "건강 데이터의 출처",
        example = "Health Connect"
    )
    private String source; // 데이터 출처

    public HealthDataRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public LocalDateTime getMeasuredAt() {
        return measuredAt;
    }

    public void setMeasuredAt(LocalDateTime measuredAt) {
        this.measuredAt = measuredAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}