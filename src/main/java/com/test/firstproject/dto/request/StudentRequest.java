package com.test.firstproject.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

// name length restriction
public record StudentRequest(

        @NotBlank(message = "Name is required")
        String name,


        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,


        @Min(value = 1, message = "Age must be greater than 0")
        Integer age

) {

}