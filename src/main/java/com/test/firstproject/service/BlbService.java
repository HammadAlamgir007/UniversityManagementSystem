package com.test.firstproject.service;

import com.test.firstproject.dto.request.SearchCustomerRequest;
import com.test.firstproject.dto.response.SearchCustomerResponse;

public interface BlbService {
    SearchCustomerResponse searchCustomer(SearchCustomerRequest request);
}
