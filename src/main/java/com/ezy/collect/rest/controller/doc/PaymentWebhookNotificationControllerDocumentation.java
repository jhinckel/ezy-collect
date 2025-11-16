package com.ezy.collect.rest.controller.doc;

import org.springframework.http.ResponseEntity;

import com.ezy.collect.rest.domain.request.PaymentWebhookRequestDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Payment notification API", description = "API for payment notification.")
public interface PaymentWebhookNotificationControllerDocumentation {

    @Operation(summary = "Add a new payment notification URL.",
            description = "This API is able to add a new URL to a payment notification.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The payment notification URL was added successfully."),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred."),
    })
    ResponseEntity<Void> registerNewWebhookUrl(PaymentWebhookRequestDto webhookRequest);

}
