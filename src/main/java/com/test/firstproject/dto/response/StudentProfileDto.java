package com.test.firstproject.dto.response;

public record StudentProfileDto(

        Long id,

        String phone,

        String address,

        String bloodGroup,

        String cnic

) {
}