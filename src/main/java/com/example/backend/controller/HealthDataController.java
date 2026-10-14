package com.example.backend.controller;

import com.example.backend.domain.HealthData;
import com.example.backend.dto.HealthDataRequest;
import com.example.backend.service.HealthDataService;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.List;

import com.example.backend.dto.InactiveResponse;

@RestController
@RequestMapping("/api/healthData")
@Tag(
    name = "건강 데이터 관리",
    description = "건강 데이터 등록, 조회, 수정, 삭제 및 사용자의 부동 상태 확인 기능"
)
public class HealthDataController {

    private final HealthDataService healthDataService;

    public HealthDataController(
            HealthDataService healthDataService) {

        this.healthDataService = healthDataService;
    }

    // 건강 데이터 등록
    @PostMapping
    @Operation(
        summary = "건강 데이터 등록",
        description = "요청 본문에 전달된 정보를 이용해 건강 데이터를 등록합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "건강 데이터 등록 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = HealthData.class)
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
    public HealthData createHealthData(
            @RequestBody(
                description = "등록할 건강 데이터 정보",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = HealthDataRequest.class)
                )
            )
            @org.springframework.web.bind.annotation.RequestBody
            HealthDataRequest request) {

        return healthDataService.createHealthData(request);
    }

    // 전체 건강 데이터 조회
    @GetMapping
    @Operation(
        summary = "전체 건강 데이터 조회",
        description = "등록된 모든 건강 데이터를 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "전체 건강 데이터 조회 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    type = "array",
                    implementation = HealthData.class
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content
        )
    })

    public List<HealthData> getAllHealthData() {

        return healthDataService.getAllHealthData();
    }

    // 특정 사용자 건강 데이터 조회
    @GetMapping("/user/{userId}")
    @Operation(
        summary = "특정 사용자 건강 데이터 조회",
        description = "사용자 ID를 기준으로 해당 사용자의 건강 데이터 목록을 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "사용자 건강 데이터 조회 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    type = "array",
                    implementation = HealthData.class
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content
        )
    })

    public List<HealthData> getHealthDataByUser(
            @Parameter(
                description = "건강 데이터를 조회할 사용자의 ID",
                example = "1",
                required = true
            )
            @PathVariable Long userId) {

        return healthDataService.getHealthDataByUser(userId);
    }

    // 건강 데이터 수정
    @PutMapping("/{id}")
    @Operation(
        summary = "건강 데이터 수정",
        description = "건강 데이터 ID와 수정할 정보를 이용해 기존 데이터를 수정합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "건강 데이터 수정 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = HealthData.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "수정 요청 데이터가 올바르지 않음",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content
        )
    })
    public HealthData updateHealthData(
        @Parameter(
                description = "수정할 건강 데이터의 ID",
                example = "1",
                required = true
            )
            @PathVariable Long id,


            @RequestBody(
                description = "수정할 건강 데이터 정보",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = HealthDataRequest.class)
                )
            )
            @org.springframework.web.bind.annotation.RequestBody
            HealthDataRequest request) {

        return healthDataService.updateHealthData(
                id,
                request
        );
    }

    // 건강 데이터 삭제
    @DeleteMapping("/{id}")
    @Operation(
        summary = "건강 데이터 삭제",
        description = "건강 데이터 ID를 이용해 해당 데이터를 삭제합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "건강 데이터 삭제 성공",
            content = @Content(
                mediaType = "text/plain",
                schema = @Schema(
                    type = "string",
                    example = "건강 데이터가 삭제되었습니다."
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content
        )
    })

    public String deleteHealthData(
            @Parameter(
                description = "삭제할 건강 데이터의 ID",
                example = "1",
                required = true
            )
            @PathVariable Long id) {

        healthDataService.deleteHealthData(id);

        return "건강 데이터가 삭제되었습니다.";
    }

    // 사용자의 부동 상태 확인
    @GetMapping("/user/{userId}/inactive")
    @Operation(
        summary = "사용자 부동 상태 확인",
        description = "사용자 ID를 이용해 해당 사용자의 부동 상태를 확인합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "부동 상태 확인 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = InactiveResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "인증 필요",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "403",
            description = "접근 권한 오류",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content
        )
    })

    public InactiveResponse checkInactive(
            @Parameter(
                description = "부동 상태를 확인할 사용자의 ID",
                example = "1"
            )
            @PathVariable Long userId) {

        return healthDataService.checkInactive(userId);
    }
}