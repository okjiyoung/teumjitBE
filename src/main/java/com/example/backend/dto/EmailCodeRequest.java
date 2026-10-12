package com.example.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "이메일 인증번호 확인 요청")
public class EmailCodeRequest {

    @Schema(
        description = "인증을 요청한 이메일 주소",
        example = "user@example.com",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String email;

    @Schema(
        description = "이메일로 전송받은 인증번호",
        example = "123456",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String code;

    public EmailCodeRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}