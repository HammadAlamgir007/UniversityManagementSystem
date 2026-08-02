

 package com.test.firstproject.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;


@Slf4j
@Component
public class RequestLoggingInterceptor
        implements HandlerInterceptor {


    private static final String START_TIME =
            "startTime";


    private static final String REQUEST_ID =
            "requestId";



    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {


        long startTime =
                System.currentTimeMillis();


        String requestId =
                UUID.randomUUID()
                        .toString()
                        .substring(0,8);



        request.setAttribute(
                START_TIME,
                startTime
        );


        request.setAttribute(
                REQUEST_ID,
                requestId
        );



        log.info(
                """
                REQUEST START
                RequestId: {}
                Method: {}
                URI: {}
                IP: {}
                """,
                requestId,
                request.getMethod(),
                request.getRequestURI(),
                request.getRemoteAddr()
        );


        return true;

    }



    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) {



        long startTime =
                (long)
                request.getAttribute(
                        START_TIME
                );


        long time =
                System.currentTimeMillis()
                        - startTime;



        String requestId =
                (String)
                request.getAttribute(
                        REQUEST_ID
                );



        log.info(
                """
                REQUEST END
                RequestId: {}
                Status: {}
                Execution Time: {} ms
                """,
                requestId,
                response.getStatus(),
                time
        );


        if(ex != null){

            log.error(
                    "Request failed",
                    ex
            );

        }


    }

}