package com.test.firstproject.dto.response;

public record LoginResponse(

        String accessToken,

        String refreshToken
) {
}