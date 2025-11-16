package com.ezy.collect.config.log;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestIdInterceptor extends OncePerRequestFilter {
	
    private static final String REQUEST_ID_HEADER = "X-Request-ID";
    private static final String REQUEST_ID_MDC_KEY = "requestId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String id = UUID.randomUUID().toString();
        
        try {
            MDC.put(REQUEST_ID_MDC_KEY, id);
            response.addHeader(REQUEST_ID_HEADER, id);
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
