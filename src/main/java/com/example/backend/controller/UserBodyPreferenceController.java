package com.example.backend.controller;

import com.example.backend.domain.UserBodyPreference;
import com.example.backend.dto.UserBodyPreferenceRequest;
import com.example.backend.service.UserBodyPreferenceService;
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
@RequestMapping("/api/userBodyPreferences")
@Tag(
    name = "사용자 신체 부위 설정",
    description = "사용자의 신체 부위 설정 등록, 조회 및 삭제 기능"
)
public class UserBodyPreferenceController {

    private final UserBodyPreferenceService preferenceService;

    public UserBodyPreferenceController(
            UserBodyPreferenceService preferenceService
    ) {
        this.preferenceService = preferenceService;
    }

    @PostMapping
    @Operation(
        summary = "신체 부위 설정 등록",
        description = "요청 본문에 전달된 정보를 이용해 사용자의 신체 부위 설정을 등록합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "신체 부위 설정 등록 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserBodyPreference.class)
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
    public UserBodyPreference createPreference(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "등록할 사용자 신체 부위 설정 정보",
                required = true,
                content = @Content(
                    schema = @Schema(
                        implementation = UserBodyPreferenceRequest.class
                    )
                )
            )
            @RequestBody UserBodyPreferenceRequest request
    ) {
        return preferenceService.createPreference(request);
    }

    @GetMapping("/{userId}")
    @Operation(
        summary = "사용자 신체 부위 설정 조회",
        description = "사용자 ID를 기준으로 해당 사용자의 신체 부위 설정 목록을 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "신체 부위 설정 목록 조회 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    type = "array",
                    implementation = UserBodyPreference.class
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content
        )
    })
    public List<UserBodyPreference> getPreferences(
            @Parameter(
                description = "설정 목록을 조회할 사용자의 ID",
                example = "1",
                required = true
            )
            @PathVariable Long userId
    ) {
        return preferenceService.getPreferences(userId);
    }

    @GetMapping("/{userId}/type/{preferenceType}")
    @Operation(
        summary = "설정 유형별 신체 부위 설정 조회",
        description = "사용자 ID와 설정 유형을 기준으로 해당하는 신체 부위 설정 목록을 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "유형별 신체 부위 설정 조회 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    type = "array",
                    implementation = UserBodyPreference.class
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content
        )
    })
    public List<UserBodyPreference> getPreferencesByType(
            @Parameter(
                description = "설정을 조회할 사용자의 ID",
                example = "1",
                required = true
            )
            @PathVariable Long userId,

            @Parameter(
                description = "조회할 설정의 유형",
                example = "UNCOMFORTABLE_BODY_PART",
                required = true
            )
            @PathVariable String preferenceType
    ) {
        return preferenceService.getPreferencesByType(
                userId,
                preferenceType
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "신체 부위 설정 삭제",
        description = "설정 ID를 이용해 해당 신체 부위 설정을 삭제합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "신체 부위 설정 삭제 성공",
            content = @Content(
                mediaType = "text/plain",
                schema = @Schema(
                    type = "string",
                    example = "신체 부위 설정이 삭제되었습니다."
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content
        )
    })
    public String deletePreference(
            @Parameter(
                description = "삭제할 신체 부위 설정의 ID",
                example = "1",
                required = true
            )
            @PathVariable Long id
    ) {
        preferenceService.deletePreference(id);

        return "신체 부위 설정이 삭제되었습니다.";
    }
}