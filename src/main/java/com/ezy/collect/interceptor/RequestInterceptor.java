package com.ezy.collect.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j(topic = "request-interceptor")
public class RequestInterceptor implements HandlerInterceptor {

    private static final int SLOW_SERVICE_RESPONSE_LIMIT_WARNING = 10000;

    private static final String EXECUTION_TIME = "execution-time";

    private static final String STATIC_CONTENT_REQUEST = "STATIC-CONTENT";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String handlerMethod = getHandlerMethod(handler, request);

        if (shouldLogRequestBody(request)) {
            String body = new String(request.getInputStream().readAllBytes());

            log.debug("\nNew request to: {} - {} ---> {}\nRequest Body: {}", request.getMethod(), request.getRequestURL(), handlerMethod, body);
        } else if (log.isInfoEnabled()) {
            log.debug("\nNew request to: {} - {} ---> {}", request.getMethod(), request.getRequestURL(), handlerMethod);
        }

        request.setAttribute(EXECUTION_TIME, System.currentTimeMillis());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (Long) request.getAttribute(EXECUTION_TIME);
        long totalTime = System.currentTimeMillis() - startTime;

        if (shouldLogRequestBody(request)) {
            ContentCachingResponseWrapper respWrapper = (ContentCachingResponseWrapper) response;
            String body = new String(respWrapper.getContentAsByteArray());

            if (totalTime > SLOW_SERVICE_RESPONSE_LIMIT_WARNING) {
                log.debug("\nRequest to: {} took {} ms\nResponse Body: {}", request.getRequestURL(), totalTime, body);
            } else {
                log.warn("\nRequest to: {} took {} ms\nResponse Body: {}", request.getRequestURL(), totalTime, body);
            }
        } else {
            if (totalTime > SLOW_SERVICE_RESPONSE_LIMIT_WARNING) {
                log.warn("\nRequest to: {} took {} ms", request.getRequestURL(), totalTime);
            } else {
                log.info("\nRequest to: {} took {} ms", request.getRequestURL(), totalTime);
            }
        }
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    private String getHandlerMethod(Object handler, HttpServletRequest request) {
        if (handler instanceof HandlerMethod) {
            return ((HandlerMethod) handler).getMethod().getName();
        } else if (handler instanceof ResourceHttpRequestHandler) {
            return STATIC_CONTENT_REQUEST + " - " + request.getRequestURI();
        }
        return StringUtils.EMPTY;
    }

    private boolean shouldLogRequestBody(HttpServletRequest request) {
        if (log.isDebugEnabled()
                && ((request.getContentType() == null) || (!request.getContentType().contains("multipart/form-data")))) {
            return true;
        }
        return false;
    }

}
