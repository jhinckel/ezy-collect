package com.ezy.collect.domain.model;

import com.ezy.collect.rest.error.FieldErrors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorMessage {

    INVALID_FIELD("ERROR-001", FieldErrors.INVALID_FIELD),
    INVALID_PARAMETER("ERROR-002", "Invalid Parameter: '%s'."),
    RESOURCE_NOT_FOUND("ERROR-003", "Resource Not Found."),
    INVALID_REQUEST("ERROR-004", "Invalid Request. '%s'."),
    UNHANDLED_ERROR("ERROR-005", "An unexpected internal error occurred."),
    METHOD_NOT_ALLOWED("ERROR-005", "Method not allowed for this resource."),

    INVALID_PAYMENT_CARD("ERROR-101", "Invalid payment card number."),
    INVALID_ZIP_CODE("ERROR-102", "Invalid ZIP code."),

	PROCESSING_ERROR("ERROR-901", "Error processing request.");
	
    private final String code;
    private final String error;

}
