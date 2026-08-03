package com.test.firstproject.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class SearchCustomerResponse {

    private String responseCode;

    private String responseDescription;

    private List<CustomerAccountInfo> customerAccountInfoVOS;

}