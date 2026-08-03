package com.test.firstproject.controller;

import com.test.firstproject.dto.request.SearchCustomerRequest;
import com.test.firstproject.dto.response.SearchCustomerResponse;
import com.test.firstproject.service.BlbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blb")
@RequiredArgsConstructor
public class BlbController {
        private final BlbService blbService;
        @PostMapping("/search-customer")
        @PreAuthorize("hasRole('USER')")
        public ResponseEntity<SearchCustomerResponse> searchCustomer(

                @RequestBody SearchCustomerRequest request) {

            return ResponseEntity.ok(
                    blbService.searchCustomer(request)

            );
        }


}
