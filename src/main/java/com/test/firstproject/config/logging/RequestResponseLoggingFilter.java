package com.test.firstproject.config.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
@Profile("dev")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);
    private static final int MAX_PAYLOAD_LENGTH = 5000;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (isAsyncDispatch(request) || (request.getContentType() != null
                && request.getContentType().startsWith("multipart/"))) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestId = UUID.randomUUID().toString().substring(0, 8);
        MDC.put("requestId", requestId);

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request,MAX_PAYLOAD_LENGTH);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            logRequest(wrappedRequest);
            logResponse(wrappedResponse, duration);
            wrappedResponse.copyBodyToResponse();
            MDC.remove("requestId");
        }
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        log.info(">>> {} {} | query={} | body={}",
                request.getMethod(), request.getRequestURI(),
                request.getQueryString(), getContentAsString(request.getContentAsByteArray()));
    }

    private void logResponse(ContentCachingResponseWrapper response, long durationMs) {
        log.info("<<< status={} | durationMs={} | body={}",
                response.getStatus(), durationMs, getContentAsString(response.getContentAsByteArray()));
    }

    private String getContentAsString(byte[] content) {
        if (content == null || content.length == 0) return "";
        int length = Math.min(content.length, MAX_PAYLOAD_LENGTH);
        String result = new String(content, 0, length, StandardCharsets.UTF_8);
        return content.length > MAX_PAYLOAD_LENGTH ? result + "...[truncated]" : result;
    }
}