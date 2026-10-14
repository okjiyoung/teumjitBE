
package com.example.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 요청")
public class LoginRequest {

    @Schema(
        description = "가입한 이메일",
        example = "teumzit@gmail.com",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String userId;

    @Schema(
        description = "비밀번호",
        example = "testpw00",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String password;

    public LoginRequest() {}

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
