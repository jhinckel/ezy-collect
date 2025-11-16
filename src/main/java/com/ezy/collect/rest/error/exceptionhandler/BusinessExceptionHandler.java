package com.ezy.collect.rest.error.exceptionhandler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ezy.collect.domain.exception.BusinessException;
import com.ezy.collect.rest.error.ErrorResponse;
import com.ezy.collect.rest.error.exception.FieldException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class BusinessExceptionHandler {

    @ExceptionHandler(FieldException.class)
    public ResponseEntity<ErrorResponse> fieldException(FieldException ex) {
        log.warn("fieldException {}", ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus())
                .body(new ErrorResponse(ex.getErrorsEnum(), new DetailedError(ex)));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> genericBusinessException(BusinessException ex) {
        Object[] interpolationParams = ex.getInterpolationParams();
        String error = ex.getError();
        String formattedMessage = String.format(error, (Object[]) interpolationParams);
        
        log.error("BusinessException code: {} error: {}", ex.getCode(), formattedMessage);
        
        return ResponseEntity.status(ex.getHttpStatus())
                .body(new ErrorResponse(ex.getCode(), formattedMessage, ex.getError(), interpolationParams));
    }

}
