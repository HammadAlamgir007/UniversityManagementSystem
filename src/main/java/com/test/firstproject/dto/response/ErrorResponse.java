package com.test.firstproject.dto.response;


import java.time.LocalDateTime;

public record ErrorResponse(

        String message,

        boolean success,

        Object data,

        LocalDateTime timestamp

) {
}