package com.ezy.collect.domain.exception;

import org.springframework.http.HttpStatus;

import com.ezy.collect.domain.model.ErrorMessage;

import lombok.Getter;

@Getter
public class MessagePublishingException extends ResourceException {

    private static final long serialVersionUID = -5190319712109863289L;

    public MessagePublishingException() {
        super(ErrorMessage.PROCESSING_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
