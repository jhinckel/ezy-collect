package com.ezy.collect.rest.error.exceptionhandler;

import com.ezy.collect.rest.error.exception.FieldException;

import lombok.Data;

@Data
public class DetailedError {
    private String field;
    private String message;

    public DetailedError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public DetailedError(FieldException ex) {
        this.field = ex.getField();
        this.message = ex.getMessage();
    }
}
