package com.ezy.collect.rest.error.exceptionhandler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;

import com.ezy.collect.rest.error.ErrorResponse;
import com.ezy.collect.rest.error.exception.RestRequestException;

import reactor.core.publisher.Mono;

@Component
public class ErrorHandler {

    public Mono<RestRequestException> handleErrorResponse(ClientResponse response) {
        Mono<ErrorResponse> errorResponseMono = response.bodyToMono(ErrorResponse.class);

        return errorResponseMono.flatMap(errorResponse -> Mono.error(new RestRequestException(response.statusCode(), errorResponse.getError())));
    }

}