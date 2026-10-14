
package com.example.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인한 사용자 정보")
public class MyInfoResponse {

    @Schema(description = "사용자 고유 ID", example = "1")
    private Long id;

    @Schema(description = "사용자 이메일", example = "teumzit@gmail.com")
    private String userId;

    public MyInfoResponse(Long id, String userId) {
        this.id = id;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }
}
