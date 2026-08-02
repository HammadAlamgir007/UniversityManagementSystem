package com.test.firstproject.dto.response;


public record StudentWithProfileResponse(

        Long studentId,

        String studentName,

        String phone,

        String address,

        String bloodGroup,

        String cnic

) {

}