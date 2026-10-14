package com.example.backend.controller;

import com.example.backend.domain.User;
import com.example.backend.dto.UserRequest;
import com.example.backend.dto.UserResponse;
import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.LoginResponse;
import com.example.backend.service.UserService;
import com.example.backend.dto.MyInfoResponse;

import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "유저 관리", description = "유저 생성 및 관리")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 유저 생성
    @Operation (
        summary = "유저 생성",
        description = "새로운 유저 생성"
    )
    @PostMapping("/users")
    public String createUser(@RequestBody UserRequest request) {
        userService.createUser(request);
        return "회원가입이 완료되었습니다.";
    }

    //유저 조회(전체)
    @Operation (
        summary = "유저 조회",
        description = "유저 전체를 조회합니다."
    )
    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(UserResponse::from)
                .toList();
    }

    //유저 정보 수정
    @Operation (
        summary = "유저 정보 수정",
        description = "유저의 정보를 수정합니다."
    )
    @PutMapping("/users/{id}")
    public UserResponse updateUser(
            @PathVariable Long id,
            @RequestBody UserRequest request) {

        User user = userService.updateUser(id, request);
        return UserResponse.from(user);
    }

    //유저 삭제
    @Operation (
        summary = "유저 삭제",
        description = "유저를 삭제합니다."
    )
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "사용자가 삭제되었습니다.";
    }

    
    // 로그인
    @Operation(
        summary = "로그인",
        description = "가입한 이메일과 비밀번호를 확인합니다."
    )
    @PostMapping("/users/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    // 사용자 정보 조회(셀프)
    @Operation(
        summary = "내 정보 조회",
        description = "JWT 인증 토큰에 포함된 사용자 이메일을 이용해 로그인한 본인의 정보를 조회합니다."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공"),
        @ApiResponse(responseCode = "401", description = "인증 토큰이 없거나 유효하지 않음"),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @GetMapping("/users/me")
    public MyInfoResponse getMyInfo(
            @io.swagger.v3.oas.annotations.Parameter(hidden = true)
            org.springframework.security.core.Authentication authentication) {

        return userService.getMyInfo(authentication.getName());
    }

}