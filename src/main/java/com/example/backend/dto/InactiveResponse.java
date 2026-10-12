package com.example.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자의 비활동 상태 확인 응답")
public class InactiveResponse {

    @Schema(
        description = "사용자가 비활동 상태인지 여부",
        example = "true"
    )
    private boolean inactive;

    @Schema(
        description = "비활동 상태로 기록된 시간(분)",
        example = "50"
    )
    private long inactiveMinutes;

    public InactiveResponse(
            boolean inactive,
            long inactiveMinutes
    ) {
        this.inactive = inactive;
        this.inactiveMinutes = inactiveMinutes;
    }

    public boolean isInactive() {
        return inactive;
    }

    public long getInactiveMinutes() {
        return inactiveMinutes;
    }
}