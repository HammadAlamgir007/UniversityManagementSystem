package com.test.firstproject.service;

import com.test.firstproject.dto.request.LoginRequest;
import com.test.firstproject.dto.request.RefreshTokenRequest;
import com.test.firstproject.dto.request.SignupRequest;
import com.test.firstproject.dto.response.LoginResponse;

public interface AuthService {

    void signup(SignupRequest request);
    LoginResponse login(LoginRequest request);
    LoginResponse refreshToken(
            RefreshTokenRequest request
    );
}