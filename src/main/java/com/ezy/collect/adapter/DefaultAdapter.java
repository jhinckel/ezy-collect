package com.ezy.collect.adapter;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.UnknownHttpStatusCodeException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.ezy.collect.adapter.exception.ExhaustedRetryException;

import lombok.extern.slf4j.Slf4j;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

@Slf4j
public abstract class DefaultAdapter {

    private static final int DEFAULT_RETRIES = 3;
    private static final int DEFAULT_RETRY_TIME_INTERVAL_SS = 5;

    protected static final Duration DEFAULT_RETRY_INTERVAL = Duration.ofSeconds(DEFAULT_RETRY_TIME_INTERVAL_SS);

    protected static final RetryBackoffSpec DEFAULT_RETRY_FILTER = Retry.fixedDelay(DEFAULT_RETRIES, DEFAULT_RETRY_INTERVAL)
            .filter(DefaultAdapter::isError)
            .onRetryExhaustedThrow((spec, signal) -> {
                throw new ExhaustedRetryException(signal.totalRetries());
            });

    @Value("${webclient.default-response-timeout-ss:10}")
    protected int defaultResponseTimeoutSs;

    private static boolean isWebClientError(Throwable throwable) {
        log.error(String.format("Http request failed! %s -> Retrying in %s seconds.", throwable.getMessage(), DEFAULT_RETRY_TIME_INTERVAL_SS));

        return throwable instanceof WebClientRequestException;
    }

    protected static boolean is5xxServerError(Throwable throwable) {
        log.error(String.format("Http request failed! %s -> Retrying in %s seconds.", throwable.getMessage(), DEFAULT_RETRY_TIME_INTERVAL_SS));

        if (throwable instanceof WebClientResponseException) {
            WebClientResponseException error = ((WebClientResponseException) throwable);

            return error.getStatusCode().is5xxServerError();
         }
        return false;
    }

    protected static boolean is4xxClientError(Throwable throwable) {
        log.error(String.format("Http request failed! %s -> Retrying in %s seconds.", throwable.getMessage(), DEFAULT_RETRY_TIME_INTERVAL_SS));

        if (throwable instanceof WebClientResponseException) {
            WebClientResponseException error = ((WebClientResponseException) throwable);

            return ((error.getStatusCode() != HttpStatus.NOT_FOUND)
                    && (error.getStatusCode() != HttpStatus.BAD_REQUEST)
                    && (error.getStatusCode() != HttpStatus.FORBIDDEN)
                    && (error.getStatusCode() != HttpStatus.UNAUTHORIZED)
                    && error.getStatusCode().is4xxClientError());
        }
        return false;
    }

    protected static boolean isError(Throwable throwable) {
        return ((throwable instanceof UnknownHttpStatusCodeException)
                || is4xxClientError(throwable)
                || is5xxServerError(throwable)
                || isWebClientError(throwable));
    }

}
