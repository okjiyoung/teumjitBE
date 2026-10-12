package com.example.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "이메일 인증번호 발송 요청")
public class EmailVerificationRequest {

    @Schema (
        description = "InputEmail",
        example = "teumzit@gmail.com",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String email;

    public EmailVerificationRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}