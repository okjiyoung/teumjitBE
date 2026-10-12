package com.example.backend.controller;

import com.example.backend.dto.EnvironmentResponse;
import com.example.backend.service.EnvironmentService;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/environment")
@Tag(
    name = "환경 정보",
    description = "사용자의 위치 정보를 바탕으로 현재 환경을 판별하는 기능"
)
public class EnvironmentController {

    private final EnvironmentService environmentService;

    public EnvironmentController(
            EnvironmentService environmentService
    ) {
        this.environmentService = environmentService;
    }

    @GetMapping("/user/{userId}")
    @Operation(
        summary = "사용자 환경 판별",
        description = "사용자 ID와 위도, 경도 정보를 이용해 현재 환경을 판별합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "사용자 환경 판별 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = EnvironmentResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "요청 매개변수가 올바르지 않음",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content
        )
    })
    public EnvironmentResponse determineEnvironment(
        @Parameter(
                description = "사용자의 ID",
                example = "1",
                required = true
            )
            @PathVariable Long userId,

            @Parameter(
                description = "사용자의 현재 위치 위도",
                example = "37.5665",
                required = true
            )
            @RequestParam double latitude,

            @Parameter(
                description = "사용자의 현재 위치 경도",
                example = "126.9780",
                required = true
            )
            @RequestParam double longitude
    ) {

        return environmentService.determineEnvironment(
                userId,
                latitude,
                longitude
        );
    }
}