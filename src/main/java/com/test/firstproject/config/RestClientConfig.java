//package com.test.firstproject.config;
//
//import com.test.firstproject.interceptor.RestClientLoggingInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestClient;
//
//@Configuration
//public class RestClientConfig {
//
//    @Bean
//    public RestClient restClient(
//            RestClientLoggingInterceptor interceptor
//    ) {
//
//        return RestClient.builder()
//                .requestInterceptor(interceptor)
//                .build();
//
//    }
//}