package com.test.firstproject.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private String responseMessage;

    private String responseCode;

    private T data;

}