package com.test.firstproject.dto.response;

import java.time.LocalDateTime;

public record StudentResponse(

        Long id,
        String name,
        String email,
        Integer age,
         LocalDateTime createdAt,
        LocalDateTime updatedAt,
        StudentProfileDto profile

) {}