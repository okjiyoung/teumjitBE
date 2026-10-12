package com.example.backend.controller;

import com.example.backend.dto.UserFavoriteStretchingRequest;
import com.example.backend.dto.UserFavoriteStretchingResponse;
import com.example.backend.service.UserFavoriteStretchingService;

import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@RestController
@RequestMapping("/api/userFavoriteStretchings")
@Tag(
    name = "사용자 스트레칭 즐겨찾기",
    description = "사용자의 스트레칭 루틴 저장, 목록 조회 및 특정 루틴 삭제 기능"
)
public class UserFavoriteStretchingController {

    private final UserFavoriteStretchingService favoriteService;

    public UserFavoriteStretchingController(
            UserFavoriteStretchingService favoriteService
    ) {
        this.favoriteService = favoriteService;
    }

    // 즐겨찾기 루틴 저장
    @PostMapping
    @Operation(
        summary = "스트레칭 루틴 즐겨찾기 등록",
        description = "여러 스트레칭 ID를 전달받아 하나의 즐겨찾기 루틴으로 저장합니다. "
                + "루틴 제목은 포함된 스트레칭의 부위를 조합해 자동 생성합니다. "
                + "동일한 구성의 루틴도 중복 저장할 수 있습니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "즐겨찾기 루틴 등록 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = UserFavoriteStretchingResponse.class
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "요청 데이터가 올바르지 않음",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content
        )
    })
    public UserFavoriteStretchingResponse createFavorite(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "사용자 ID와 루틴에 포함할 스트레칭 ID 목록",
                required = true,
                content = @Content(
                    schema = @Schema(
                        implementation = UserFavoriteStretchingRequest.class
                    )
                )
            )
            @RequestBody UserFavoriteStretchingRequest request
    ) {
        return favoriteService.createFavorite(request);
    }

    // 특정 사용자의 즐겨찾기 루틴 목록 조회
    @GetMapping("/{userId}")
    @Operation(
        summary = "사용자 즐겨찾기 루틴 목록 조회",
        description = "사용자 ID를 기준으로 저장된 루틴 목록을 최신 저장 순서대로 조회합니다. "
                + "각 루틴의 제목과 포함된 스트레칭 목록도 반환합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "즐겨찾기 루틴 목록 조회 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    type = "array",
                    implementation = UserFavoriteStretchingResponse.class
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content
        )
    })
    public List<UserFavoriteStretchingResponse> getFavorites(
            @Parameter(
                description = "즐겨찾기 목록을 조회할 사용자의 ID",
                example = "1",
                required = true
            )
            @PathVariable Long userId
    ) {
        return favoriteService.getFavorites(userId);
    }

    // 특정 즐겨찾기 루틴 삭제
    @DeleteMapping("/{id}")
    @Operation(
        summary = "즐겨찾기 루틴 삭제",
        description = "루틴 ID를 이용해 해당 루틴과 연결된 항목을 삭제합니다. "
                + "기존 스트레칭 데이터는 삭제하지 않습니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "즐겨찾기 루틴 삭제 성공",
            content = @Content(
                mediaType = "text/plain",
                schema = @Schema(
                    type = "string",
                    example = "즐겨찾기 루틴이 삭제되었습니다."
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content
        )
    })
    public String deleteFavorite(
            @Parameter(
                description = "삭제할 즐겨찾기 루틴의 ID",
                example = "101",
                required = true
            )
            @PathVariable Long id
    ) {
        favoriteService.deleteFavorite(id);

        return "즐겨찾기 루틴이 삭제되었습니다.";
    }
}