
package com.example.backend.controller;

import com.example.backend.dto.EmailCodeRequest;
import com.example.backend.dto.EmailVerificationRequest;
import com.example.backend.service.EmailVerificationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/email-verifications")
@Tag(
    name = "이메일 인증",
    description = "회원가입 시 사용하는 이메일 인증번호 발송 및 인증 기능"
)
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    public EmailVerificationController(
            EmailVerificationService emailVerificationService
    ) {
        this.emailVerificationService = emailVerificationService;
    }

    // 인증번호 발송
    @PostMapping("/send")
    @Operation(
        summary = "이메일 인증번호 발송",
        description = "입력한 이메일 주소로 인증번호를 발송합니다. "
                    + "이메일 형식이 올바르지 않거나 이미 가입된 이메일인 경우 요청이 거부될 수 있습니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "인증번호 이메일 발송 성공",
            content = @Content(
                mediaType = "text/plain",
                schema = @Schema(
                    type = "string",
                    example = "인증번호를 이메일로 전송했습니다."
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "잘못된 이메일 주소 또는 인증번호 발송 요청",
            content = @Content(
                mediaType = "text/plain",
                schema = @Schema(
                    type = "string",
                    example = "이메일 주소가 올바르지 않습니다."
                )
            )
        )
    })
    public ResponseEntity<String> sendCode(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "인증번호를 받을 이메일 주소",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = EmailVerificationRequest.class)
                )
            )
            @RequestBody EmailVerificationRequest request
    ) {
        try {
            emailVerificationService.sendCode(request.getEmail());

            return ResponseEntity.ok(
                    "인증번호를 이메일로 전송했습니다."
            );

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 인증번호 확인
    @PostMapping("/verify")
    @Operation(
        summary = "이메일 인증번호 확인",
        description = "이메일 주소와 해당 주소로 발송된 인증번호를 확인합니다. "
                    + "인증번호가 유효하면 이메일 인증을 완료합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "이메일 인증 성공",
            content = @Content(
                mediaType = "text/plain",
                schema = @Schema(
                    type = "string",
                    example = "이메일 인증이 완료되었습니다."
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "인증번호가 올바르지 않거나 만료되는 등 이메일 인증 실패",
            content = @Content(
                mediaType = "text/plain",
                schema = @Schema(
                    type = "string",
                    example = "인증번호가 올바르지 않습니다."
                )
            )
        )
    })
    public ResponseEntity<String> verifyCode(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "인증번호를 발송받은 이메일 주소와 인증번호",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = EmailCodeRequest.class)
                )
            )
            @RequestBody EmailCodeRequest request
    ) {
        try {
            emailVerificationService.verifyCode(
                    request.getEmail(),
                    request.getCode()
            );

            return ResponseEntity.ok(
                    "이메일 인증이 완료되었습니다."
            );

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
