package com.example.backend.controller;

import com.example.backend.domain.Stretching;
import com.example.backend.dto.StretchingDifficultyRequest;
import com.example.backend.dto.StretchingDifficultyResponse;
import com.example.backend.service.StretchingDifficultyService;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/api/stretching-difficulties")
@Tag(
    name = "스트레칭 난이도 조건 관리",
    description = "스트레칭 난이도 조건 조회, 등록, 수정, 삭제 및 조건별 스트레칭 조회 기능"
)
public class StretchingDifficultyController {

    private final StretchingDifficultyService service;

    public StretchingDifficultyController(
            StretchingDifficultyService service) {

        this.service = service;
    }

    // 모든 difficulty 조회
    @GetMapping
    @Operation(
        summary = "모든 스트레칭 조건 조회",
        description = "등록된 모든 스트레칭 조건 목록을 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "스트레칭 조건 목록 조회 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    type = "array",
                    implementation = StretchingDifficultyResponse.class
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content
        )
    })
    public ResponseEntity<List<StretchingDifficultyResponse>> getAll() {

        return ResponseEntity.ok(
                service.getAll()
        );
    }

    // difficulty ID로 조회
    @GetMapping("/{id}")
    @Operation(
        summary = "스트레칭 조건 단건 조회",
        description = "조건 ID를 이용해 특정 스트레칭 조건의 정보를 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "스트레칭 조건 조회 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = StretchingDifficultyResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content
        )
    })
    public ResponseEntity<StretchingDifficultyResponse> getById(
            @Parameter(
                description = "조회할 스트레칭 조건의 ID",
                example = "1",
                required = true
            )
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getById(id)
        );
    }

    // 특정 stretching의 difficulty 조회
    @GetMapping("/stretching/{stretchingId}")
    @Operation(
        summary = "특정 스트레칭의 조건 조회",
        description = "스트레칭 ID를 기준으로 해당 스트레칭에 연결된 조건 목록을 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "해당 스트레칭의 조건 목록 조회 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    type = "array",
                    implementation = StretchingDifficultyResponse.class
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content
        )
    })
    public ResponseEntity<List<StretchingDifficultyResponse>> getByStretchingId(
            @Parameter(
                description = "조건 목록을 조회할 스트레칭의 ID",
                example = "1",
                required = true
            )
            @PathVariable Long stretchingId) {

        return ResponseEntity.ok(
                service.getByStretchingId(stretchingId)
        );
    }

    // 여러 difficulty 조건으로 스트레칭 조회
    @GetMapping("/stretchings")
    @Operation(
        summary = "여러 조건에 해당하는 스트레칭 조회",
        description = "difficulty 매개변수에 전달된 여러 조건을 이용해 스트레칭 목록을 조회합니다. "
                    + "같은 매개변수를 반복하여 여러 조건을 전달할 수 있습니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "조건에 해당하는 스트레칭 목록 조회 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    type = "array",
                    implementation = Stretching.class
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "조건 매개변수가 올바르지 않음",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content
        )
    })
    public ResponseEntity<List<Stretching>> getStretchingsByDifficulties(
            @Parameter(
                description = "조회에 사용할 스트레칭 조건 목록. 여러 조건은 difficulty 매개변수를 반복하여 전달합니다.",
                example = "BOTH_HANDS",
                required = true
            )
            @RequestParam List<String> difficulty) {

        return ResponseEntity.ok(
                service.getStretchingsByDifficulties(difficulty)
        );
    }

    // difficulty 등록
    @PostMapping
    @Operation(
        summary = "스트레칭 조건 등록",
        description = "요청 본문에 전달된 정보를 이용해 새로운 스트레칭 조건을 등록합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "스트레칭 조건 등록 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = StretchingDifficultyResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "등록 요청 데이터가 올바르지 않음",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content
        )
    })
    public ResponseEntity<StretchingDifficultyResponse> create(
            @RequestBody(
                description = "등록할 스트레칭 조건 정보",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = StretchingDifficultyRequest.class)
                )
            )
            @org.springframework.web.bind.annotation.RequestBody
            StretchingDifficultyRequest request) {

        return ResponseEntity.ok(
                service.create(request)
        );
    }

    // difficulty 수정
    @PutMapping("/{id}")
    @Operation(
        summary = "스트레칭 조건 수정",
        description = "조건 ID와 수정할 정보를 이용해 기존 스트레칭 조건을 수정합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "스트레칭 조건 수정 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = StretchingDifficultyResponse.class)
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
    public ResponseEntity<StretchingDifficultyResponse> update(
            @Parameter(
                description = "수정할 스트레칭 조건의 ID",
                example = "1",
                required = true
            )
            @PathVariable Long id,

            @RequestBody(
                description = "수정할 스트레칭 조건 정보",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = StretchingDifficultyRequest.class)
                )
            )
            @org.springframework.web.bind.annotation.RequestBody
            StretchingDifficultyRequest request) {

        return ResponseEntity.ok(
                service.update(id, request)
        );
    }

    // difficulty 삭제
    @DeleteMapping("/{id}")
    @Operation(
        summary = "스트레칭 조건 삭제",
        description = "조건 ID를 이용해 해당 스트레칭 조건을 삭제합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "스트레칭 조건 삭제 성공"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content
        )
    })
    public ResponseEntity<Void> delete(
            @Parameter(
                description = "삭제할 스트레칭 조건의 ID",
                example = "1",
                required = true
            )
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}