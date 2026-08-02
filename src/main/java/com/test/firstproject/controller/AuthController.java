package com.test.firstproject.controller;

import com.test.firstproject.dto.request.LoginRequest;
import com.test.firstproject.dto.request.SignupRequest;
import com.test.firstproject.dto.response.ApiResponse;
import com.test.firstproject.dto.response.LoginResponse;
import com.test.firstproject.service.AuthService;
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
}
