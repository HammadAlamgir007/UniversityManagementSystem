//package com.test.firstproject.interceptor;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpRequest;
//import org.springframework.http.client.ClientHttpRequestExecution;
//import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.http.client.ClientHttpResponse;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Slf4j
//@Component
//public class RestClientLoggingInterceptor
//        implements ClientHttpRequestInterceptor {
//
//    @Override
//    public ClientHttpResponse intercept(
//
//            HttpRequest request,
//
//            byte[] body,
//
//            ClientHttpRequestExecution execution)
//
//            throws IOException {
//
//        long startTime = System.currentTimeMillis();
//
//        log.info("========== OUTGOING API REQUEST ==========");
//
//        log.info("Method : {}", request.getMethod());
//
//        log.info("URI    : {}", request.getURI());
//
//        log.debug("Headers: {}", request.getHeaders());
//
//        if (body.length > 0) {
//
//            log.debug("Body   : {}", new String(body));
//
//        }
//
//        ClientHttpResponse response =
//                execution.execute(request, body);
//
//        long endTime = System.currentTimeMillis();
//
//        log.info("Status : {}", response.getStatusCode());
//
//        log.info("Time   : {} ms",
//                endTime - startTime);
//
//        log.info("==========================================");
//
//        return response;
//
//    }
//
//}