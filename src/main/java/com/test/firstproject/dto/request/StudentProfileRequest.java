package com.test.firstproject.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record StudentProfileRequest(
        @NotBlank(message = "phone is required")
        @Pattern(regexp = "^\\d{4}-\\d{7}$", message = "Phone must match format XXXX-XXXXXXX")
        String phone,

        String address,

        // validaton O+, A-, AB+
        String bloodGroup,
        @NotBlank(message = "CNIC is required")
        @Pattern(regexp = "^\\d{5}-\\d{7}-\\d{1}$", message = "CNIC must match format XXXXX-XXXXXXX-X")
        String cnic,
//        @NotBlank(message = "Must upload any file")
//        String profileImage,
        Long studentId

) {
}