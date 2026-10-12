package com.example.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 프로필 등록 및 수정 요청")
public class UserProfileRequest {

    @Schema(
        description = "사용자 ID",
        example = "1"
    )
    private Long userId;

    @Schema(
        description = "사용자 이메일 주소",
        example = "user@example.com"
    )
    private String email;

    @Schema(
        description = "사용자 닉네임",
        example = "티미"
    )
    private String nickname;

    @Schema(
        description = "사용자의 집 주소",
        example = "서울특별시 종로구"
    )
    private String homeAddress;

    @Schema(
        description = "집 위치의 위도",
        example = "37.5665"
    )
    private Double homeLatitude;

    @Schema(
        description = "집 위치의 경도",
        example = "126.9780"
    )
    private Double homeLongitude;

    public UserProfileRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Double getHomeLatitude() {
        return homeLatitude;
    }

    public void setHomeLatitude(Double homeLatitude) {
        this.homeLatitude = homeLatitude;
    }

    public Double getHomeLongitude() {
        return homeLongitude;
    }

    public void setHomeLongitude(Double homeLongitude) {
        this.homeLongitude = homeLongitude;
    }
}