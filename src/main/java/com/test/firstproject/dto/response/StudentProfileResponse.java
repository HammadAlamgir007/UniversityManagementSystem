package com.test.firstproject.dto.response;

public record StudentProfileResponse(

        Long id,

        String phone,

        String address,

        String bloodGroup,

        String cnic,

        Long studentId,

        String studentName,

        String imageUrl
) {
}
