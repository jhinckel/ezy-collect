package com.ezy.collect.domain.exception;

import org.springframework.http.HttpStatus;

import com.ezy.collect.domain.model.ErrorMessage;

import lombok.Getter;

@Getter
public class InvalidZipCodeException extends BusinessException {

    private static final long serialVersionUID = -5190319712109863289L;

    public InvalidZipCodeException() {
        super(ErrorMessage.INVALID_PAYMENT_CARD, HttpStatus.BAD_REQUEST);
    }

}
