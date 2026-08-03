package com.test.firstproject.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private String responseMessage;

    private String responseCode;

    private T data;
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(message, "99", null);
    }

}