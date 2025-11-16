package com.ezy.collect.rest.error.dto;

import com.ezy.collect.rest.error.exception.FieldException;

import lombok.Data;

@Data
public class DetailedErrorDto {

    private String field;
    private String message;

    public DetailedErrorDto(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public DetailedErrorDto(FieldException ex) {
        this.field = ex.getField();
        this.message = ex.getMessage();
    }

}
