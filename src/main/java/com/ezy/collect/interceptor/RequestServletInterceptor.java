package com.ezy.collect.interceptor;

import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "request-interceptor")
public class RequestServletInterceptor extends DispatcherServlet {

    private static final long serialVersionUID = 1078562872128482579L;

    public RequestServletInterceptor() {
        super.setThrowExceptionIfNoHandlerFound(true);
        // Set to TRUE and enable TRACE log to show request header.
        super.setEnableLoggingRequestDetails(false);
    }

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (shouldLogRequestBody(request)) {
            ReusableHttpServletRequestWrapper reusableRequest = new ReusableHttpServletRequestWrapper(request);
            ContentCachingResponseWrapper respWrapper = new ContentCachingResponseWrapper(response);

            try {
                super.doDispatch(reusableRequest, respWrapper);
            } finally {
                respWrapper.copyBodyToResponse();
            }
        } else {
            super.doDispatch(request, response);
        }
    }

    private boolean shouldLogRequestBody(HttpServletRequest request) {
        if (log.isDebugEnabled()
                && ((request.getContentType() == null) || (!request.getContentType().contains("multipart/form-data")))) {
            return true;
        }
        return false;
    }

}
