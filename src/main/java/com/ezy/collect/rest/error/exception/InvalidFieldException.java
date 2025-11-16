package com.ezy.collect.rest.error.exception;

import com.ezy.collect.rest.error.FieldErrors;

public class InvalidFieldException extends FieldException {

    private static final long serialVersionUID = -2366198936569495073L;

    public InvalidFieldException(String field) {
        super(field, FieldErrors.formatMessage(FieldErrors.REQUIRED_VALUE, field));
    }

    public InvalidFieldException(String field, Object value) {
        super(field, FieldErrors.formatMessage(FieldErrors.INVALID_VALUE, field, value));
    }

}
