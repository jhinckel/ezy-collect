package com.ezy.collect.rest.error.exception;

import org.springframework.http.HttpStatusCode;

import lombok.Getter;

public class RestRequestException extends RuntimeException {

    private static final long serialVersionUID = 4536634748228281716L;

    @Getter
    private int httpStatusCode;

    public RestRequestException(int httpStatusCode, String message) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }

    public RestRequestException(HttpStatusCode httpStatus, String message) {
        super(message);
        this.httpStatusCode = httpStatus.value();
    }

}
