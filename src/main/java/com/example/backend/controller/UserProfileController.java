package com.example.backend.controller;

import com.example.backend.domain.UserProfile;
import com.example.backend.dto.UserProfileRequest;
import com.example.backend.service.UserProfileService;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/userProfile")
@Tag(
    name = "사용자 프로필",
    description = "사용자 프로필 생성, 조회 및 수정 기능"
)
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(
            UserProfileService userProfileService
    ) {
        this.userProfileService = userProfileService;
    }

    @PostMapping
    @Operation(
        summary = "사용자 프로필 생성",
        description = "요청 본문에 전달된 정보를 이용해 사용자 프로필을 생성합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "사용자 프로필 생성 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserProfile.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "요청 데이터가 올바르지 않음",
            content = @Content
        )
    })
    public UserProfile createProfile(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "생성할 사용자 프로필 정보",
                required = true,
                content = @Content(
                    schema = @Schema(
                        implementation = UserProfileRequest.class
                    )
                )
            )
            @RequestBody UserProfileRequest request
    ) {
        return userProfileService.createProfile(request);
    }

    @GetMapping("/{userId}")
    @Operation(
        summary = "사용자 프로필 조회",
        description = "사용자 ID를 기준으로 해당 사용자의 프로필을 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "사용자 프로필 조회 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserProfile.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content
        )
    })
    public UserProfile getProfile(
            @Parameter(
                description = "프로필을 조회할 사용자의 ID",
                example = "1",
                required = true
            )
            @PathVariable Long userId
    ) {
        return userProfileService.getProfile(userId);
    }

    @PutMapping("/{userId}")
    @Operation(
        summary = "사용자 프로필 수정",
        description = "사용자 ID와 요청 본문에 전달된 정보를 이용해 프로필을 수정합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "사용자 프로필 수정 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserProfile.class)
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
    public UserProfile updateProfile(
            @Parameter(
                description = "수정할 프로필 소유자의 사용자 ID",
                example = "1",
                required = true
            )
            @PathVariable Long userId,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "수정할 사용자 프로필 정보",
                required = true,
                content = @Content(
                    schema = @Schema(
                        implementation = UserProfileRequest.class
                    )
                )
            )
            @RequestBody UserProfileRequest request
    ) {
        return userProfileService.updateProfile(
                userId,
                request
        );
    }
}