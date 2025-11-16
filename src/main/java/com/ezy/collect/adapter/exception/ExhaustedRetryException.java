package com.ezy.collect.adapter.exception;

public class ExhaustedRetryException extends RuntimeException {

	private static final long serialVersionUID = 8961072894465169916L;

	public ExhaustedRetryException(long totalRetries) {
        super(String.format("Service call failed even after retrying %s times", totalRetries));
    }
}
