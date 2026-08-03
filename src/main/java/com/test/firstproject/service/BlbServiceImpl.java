package com.test.firstproject.service;

import com.test.firstproject.client.BlbClient;
import com.test.firstproject.dto.request.SearchCustomerRequest;
import com.test.firstproject.dto.response.SearchCustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlbServiceImpl implements BlbService {

        private final BlbClient blbClient;

        @Override
            public SearchCustomerResponse searchCustomer(SearchCustomerRequest request){
            return blbClient.searchCustomer(request);
        }
}
