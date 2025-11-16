package com.ezy.collect.rest.error.exception;

import com.ezy.collect.rest.error.FieldErrors;

public class FieldRequiredException extends FieldException {

    private static final long serialVersionUID = 7566703136077830576L;

    public FieldRequiredException(String field) {
        super(field, FieldErrors.formatMessage(FieldErrors.REQUIRED_VALUE, field));
    }
    
}
