package com.example.backend.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "사용자 스트레칭 즐겨찾기 등록 요청")
public class UserFavoriteStretchingRequest {

    @Schema(
        description = "즐겨찾기를 등록할 사용자의 ID",
        example = "1"
    )
    private Long userId;

    @ArraySchema(
        arraySchema = @Schema(
            description = "즐겨찾기에 등록할 스트레칭 ID 목록. 배열 순서가 루틴 실행 순서가 됩니다."
        ),
        schema = @Schema(
            type = "integer",
            format = "int64",
            example = "10"
        )
    )
    private List<Long> stretchingIds;

    public UserFavoriteStretchingRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getStretchingIds() {
        return stretchingIds;
    }

    public void setStretchingIds(List<Long> stretchingIds) {
        this.stretchingIds = stretchingIds;
    }
}