package com.ezy.collect.rest.error;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import com.ezy.collect.domain.model.ErrorMessage;
import com.ezy.collect.rest.error.exceptionhandler.DetailedError;

import lombok.Data;

@Data
public class ErrorResponse {
	
    private String code;
    private String error;
    private String message;
    private Object[] interpolationParams;
    private List<DetailedError> detailedError;
    private LocalDateTime timestamp = LocalDateTime.now();

    public ErrorResponse(ErrorMessage errorsEnum) {
        code = errorsEnum.getCode();
        error = errorsEnum.getError();
        message = error;
    }

    public ErrorResponse(ErrorMessage errorsEnum, DetailedError detailedError) {
        code = errorsEnum.getCode();
        error = errorsEnum.getError();
        this.detailedError = Collections.singletonList(detailedError);
    }

    public ErrorResponse(ErrorMessage errorsEnum, List<DetailedError> detailedErrors) {
        code = errorsEnum.getCode();
        error = errorsEnum.getError();
        detailedError = detailedErrors;
    }

    public ErrorResponse(String code, String error, String message, Object... interpolationParams) {
        this.code = code;
        this.error = error;
        this.message = message;
        this.interpolationParams = interpolationParams;
    }

    public ErrorResponse(String code, String error) {
        this.code = code;
        this.error = error;
    }
}
