package com.ezy.collect.rest.error.exception;

import org.springframework.http.HttpStatus;

import com.ezy.collect.domain.exception.BusinessException;
import com.ezy.collect.domain.model.ErrorMessage;

import lombok.Getter;

public abstract class FieldException extends BusinessException {

    private static final long serialVersionUID = 3895274015986579871L;

    @Getter
    private final String field;

    @Getter
    private final String message;

    public FieldException(String field, String message) {
        super(ErrorMessage.INVALID_FIELD, HttpStatus.BAD_REQUEST);
        this.field = field;
        this.message = message;
    }
    
}
