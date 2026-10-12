package com.example.backend.controller;

import com.example.backend.domain.User;
import com.example.backend.dto.UserRequest;
import com.example.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

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

        return "사용자가 저장되었습니다.";
    }

    //유저 조회(전체)
    @Operation (
        summary = "유저 조회",
        description = "유저 전체를 조회합니다."
    )
    @GetMapping("/users")
    public List<User> getAllUsers() {

        return userService.getAllUsers();
    }

    //유저 정보 수정
    @Operation (
        summary = "유저 정보 수정",
        description = "유저의 정보를 수정합니다."
    )
    @PutMapping("/users/{id}")
    public User updateUser(
            @PathVariable Long id,
            @RequestBody UserRequest request) {

        return userService.updateUser(id, request);
    }

    //유저 삭제
    @Operation (
        summary = "유저 삭제",
        description = "유저를 삭제합니다."
    )
    @DeleteMapping("/users/{id}")
    public String deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return "사용자가 삭제되었습니다.";
    }
}