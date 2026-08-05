package com.test.firstproject.controller;

import com.test.firstproject.dto.request.*;
import com.test.firstproject.dto.response.ApiResponse;
import com.test.firstproject.dto.response.LoginResponse;

import com.test.firstproject.service.AuthService;

import com.test.firstproject.service.PasswordResetService;
import com.test.firstproject.service.email.EmailService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PasswordResetService passwordResetService;
    private final EmailService emailService;
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>>Signup(
            @Valid
            @RequestBody
            SignupRequest request
    ){
        authService.signup(request);
        ApiResponse<Void> response =
                new ApiResponse<>("User registered successfully", "00", null);
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid
            @RequestBody LoginRequest request) {

        LoginResponse loginResponse =
                authService.login(request);

        ApiResponse<LoginResponse> response =
                new ApiResponse<>(

                        "Login successful",

                        "00",

                        loginResponse

                );

        return ResponseEntity.ok(response);
    }
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponse>> refreshToken(

            @RequestBody RefreshTokenRequest request

    ){


        LoginResponse response =
                authService.refreshToken(request);



        return ResponseEntity.ok(

                new ApiResponse<>(

                        "Token refreshed successfully",

                        "00",

                        response

                )

        );

    }
    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<Void>> forgotPassword(
            @Valid
            @RequestBody
            ForgotPasswordRequest request
    ) {

        passwordResetService.forgotPassword(
                request.getEmail()
        );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Password reset email sent.",
                        "00",
                        null
                )
        );

    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(
            @Valid
            @RequestBody
            ResetPasswordRequest request
    ) {
        passwordResetService.resetPassword(request);

        return ResponseEntity.ok(

                new ApiResponse<>(

                        "Password changed successfully.",

                        "00",

                        null

                )

        );

    }
        @GetMapping("/email")
        public String sendTestEmail()
        {

            emailService.sendEmail(
                    "hammadalamgir778@gmail.com",
                    "Spring Boot Email Test",
                    "Congratulations! Your email configuration is working."
            );

            return "Email Sent Successfully";
        }
    }


