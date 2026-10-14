
package com.example.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 응답")
public class LoginResponse {

    @Schema(description = "로그인 결과", example = "로그인에 성공했습니다.")
    private String message;

    @Schema(description = "사용자 ID", example = "1")
    private Long id;

    @Schema(description = "사용자 이메일", example = "teumzit@gmail.com")
    private String userId;

    public LoginResponse() {}

    public LoginResponse(String message, Long id, String userId) {
        this.message = message;
        this.id = id;
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    // 토큰 필드
    @Schema(description = "JWT 인증 토큰")
    private String token;

    public LoginResponse(
            String message, Long id, String userId, String token) {
        this.message = message;
        this.id = id;
        this.userId = userId;
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
