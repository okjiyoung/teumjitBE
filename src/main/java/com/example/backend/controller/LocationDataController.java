package com.example.backend.controller;

import com.example.backend.domain.LocationData;
import com.example.backend.dto.LocationDataRequest;
import com.example.backend.service.LocationDataService;
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
@RequestMapping("/api/locationData")
@Tag(
    name = "위치 데이터 관리",
    description = "위치 데이터 등록 및 전체 조회, 사용자별 조회 기능"
)
public class LocationDataController {

    private final LocationDataService locationDataService;

    public LocationDataController(
            LocationDataService locationDataService
    ) {
        this.locationDataService = locationDataService;
    }

    @PostMapping
    @Operation(
        summary = "위치 데이터 등록",
        description = "요청 본문에 전달된 정보를 이용해 위치 데이터를 등록합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "위치 데이터 등록 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = LocationData.class)
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
    public LocationData createLocationData(
            @RequestBody(
                description = "등록할 위치 데이터 정보",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = LocationDataRequest.class)
                )
            )
            @org.springframework.web.bind.annotation.RequestBody
            LocationDataRequest request
    ) {
        return locationDataService.createLocationData(request);
    }

    @GetMapping
    @Operation(
        summary = "전체 위치 데이터 조회",
        description = "등록된 모든 위치 데이터를 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "전체 위치 데이터 조회 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    type = "array",
                    implementation = LocationData.class
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content
        )
    })
    public List<LocationData> getAllLocationData() {
        return locationDataService.getAllLocationData();
    }

    @GetMapping("/user/{userId}")
    @Operation(
        summary = "특정 사용자 위치 데이터 조회",
        description = "사용자 ID를 기준으로 해당 사용자의 위치 데이터 목록을 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "사용자 위치 데이터 조회 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    type = "array",
                    implementation = LocationData.class
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류",
            content = @Content
        )
    })
    public List<LocationData> getLocationDataByUser(
            @Parameter(
                description = "위치 데이터를 조회할 사용자의 ID",
                example = "1",
                required = true
            )
            @PathVariable Long userId
    ) {
        return locationDataService.getLocationDataByUser(userId);
    }
}