package com.test.firstproject.config;


import com.test.firstproject.interceptor.RequestLoggingInterceptor;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
@RequiredArgsConstructor
public class WebConfig
        implements WebMvcConfigurer {


    private final RequestLoggingInterceptor interceptor;



    @Override
    public void addInterceptors(
            InterceptorRegistry registry
    ) {

        registry.addInterceptor(interceptor).excludePathPatterns(
                "/swagger-ui/**",
                "/v3/api-docs/**"
        );

    }

}