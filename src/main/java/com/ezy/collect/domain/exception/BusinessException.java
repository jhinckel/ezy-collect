package com.ezy.collect.domain.exception;

import org.springframework.http.HttpStatus;

import com.ezy.collect.domain.model.ErrorMessage;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -2364928916704244335L;

	private final ErrorMessage errorsEnum;
    private final HttpStatus httpStatus;
    private final Object[] interpolationParams;

    public BusinessException(ErrorMessage errorEnum, HttpStatus httpStatus) {
        super(errorEnum.getError());
        this.errorsEnum = errorEnum;
        this.httpStatus = httpStatus;
        this.interpolationParams = new String[0];
    }

    public BusinessException(ErrorMessage errorEnum, HttpStatus httpStatus, String... interpolationParams) {
        super(errorEnum.getError());
        this.errorsEnum = errorEnum;
        this.httpStatus = httpStatus;
        this.interpolationParams = interpolationParams;
    }

    public BusinessException(ErrorMessage errorEnum, String... interpolationParams) {
        super(errorEnum.getError());
        this.errorsEnum = errorEnum;
        this.httpStatus = null;
        this.interpolationParams = interpolationParams;
    }

    public String getError() {
        return errorsEnum.getError();
    }

    public String getCode() {
        return errorsEnum.getCode();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String toString() {
        if (httpStatus == null) {
            return String.format(getError(), getInterpolationParams());
        }
        return String.format(String.format("%s(%s) - %s", getHttpStatus().name(), getHttpStatus().value(), getError()), getInterpolationParams());
    }
}

