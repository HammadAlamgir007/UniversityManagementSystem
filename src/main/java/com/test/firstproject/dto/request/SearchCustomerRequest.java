package com.test.firstproject.dto.request;

public record SearchCustomerRequest(

        String name,

        String cnic,

        String mobileNo,

        String iban

) {
}