package com.example.backend.controller;

import com.example.backend.domain.Stretching;
import com.example.backend.dto.StretchingRequest;
import com.example.backend.service.StretchingService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/stretchings")
@Tag(
    name = "스트레칭 관리",
    description = "스트레칭 등록, 전체 조회, 개별 조회, 수정 및 삭제 기능"
)
public class StretchingController {

    private final StretchingService stretchingService;

    public StretchingController(StretchingService stretchingService) {
        this.stretchingService = stretchingService;
    }

    // 스트레칭 등록
    @PostMapping
    @Operation(
        summary = "스트레칭 등록",
        description = "요청 본문에 전달된 정보를 이용해 새로운 스트레칭을 등록합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "스트레칭 등록 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Stretching.class)
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
    public Stretching createStretching(
            @RequestBody(
                description = "등록할 스트레칭 정보",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = StretchingRequest.class)
                )
            )
            @org.springframework.web.bind.annotation.RequestBody
            StretchingRequest request) {

        return stretchingService.createStretching(request);
    }

    // 스트레칭 전체 조회
    @GetMapping
    @Operation(
        summary = "스트레칭 전체 조회",
        description = "등록된 모든 스트레칭 정보를 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "스트레칭 전체 조회 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    type = "array",
                    implementation = Stretching.class
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content
        )
    })
    public List<Stretching> getAllStretchings() {

        return stretchingService.getAllStretchings();
    }

    // 스트레칭 ID로 조회
    @GetMapping("/{id}")
    @Operation(
        summary = "스트레칭 단건 조회",
        description = "스트레칭 ID를 이용해 특정 스트레칭의 정보를 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "스트레칭 조회 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Stretching.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content
        )
    })
    public Stretching getStretching(
            @Parameter(
                description = "조회할 스트레칭의 ID",
                example = "1",
                required = true
            )
            @PathVariable Long id) {

        return stretchingService.getStretching(id);
    }

    // 스트레칭 수정
    @PutMapping("/{id}")
    @Operation(
        summary = "스트레칭 수정",
        description = "스트레칭 ID와 수정할 정보를 이용해 기존 스트레칭 정보를 수정합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "스트레칭 수정 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Stretching.class)
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
    public Stretching updateStretching(
            @Parameter(
                description = "수정할 스트레칭의 ID",
                example = "1",
                required = true
            )
            @PathVariable Long id,

            @RequestBody(
                description = "수정할 스트레칭 정보",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = StretchingRequest.class)
                )
            )
            @org.springframework.web.bind.annotation.RequestBody
            StretchingRequest request) {

        return stretchingService.updateStretching(id, request);
    }

    // 스트레칭 삭제
    @DeleteMapping("/{id}")
    @Operation(
        summary = "스트레칭 삭제",
        description = "스트레칭 ID를 이용해 해당 스트레칭을 삭제합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "스트레칭 삭제 성공",
            content = @Content(
                mediaType = "text/plain",
                schema = @Schema(
                    type = "string",
                    example = "스트레칭이 삭제되었습니다."
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content
        )
    })
    public String deleteStretching(
            @Parameter(
                description = "삭제할 스트레칭의 ID",
                example = "1",
                required = true
            )
            @PathVariable Long id) {

        stretchingService.deleteStretching(id);

        return "스트레칭이 삭제되었습니다.";
    }
}