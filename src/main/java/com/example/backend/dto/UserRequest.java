package com.example.backend.dto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 로그인 요청")
public class UserRequest {

    @Schema (
        description = "사용자 ID",
        example = "testuser00",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String userId;

    @Schema (
        description = "사용자 PW",
        example = "testpw00",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String password;

    public UserRequest() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}