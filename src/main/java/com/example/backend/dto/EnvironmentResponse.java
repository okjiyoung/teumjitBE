package com.example.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

//추후 다른 환경 요소도 추가할 예정.

@Schema(description = "사용자의 현재 환경 정보 응답")
public class EnvironmentResponse {

    @Schema(
        description = "판별된 사용자 환경",
        example = "HOME"
    )
    private String environment;

    @Schema(
        description = "집으로부터의 거리",
        example = "150.5"
    )
    private double distanceFromHome;

    public EnvironmentResponse(
            String environment,
            double distanceFromHome
    ) {
        this.environment = environment;
        this.distanceFromHome = distanceFromHome;
    }

    public String getEnvironment() {
        return environment;
    }

    public double getDistanceFromHome() {
        return distanceFromHome;
    }
}