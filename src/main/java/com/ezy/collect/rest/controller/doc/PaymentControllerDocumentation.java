package com.ezy.collect.rest.controller.doc;

import org.springframework.http.ResponseEntity;

import com.ezy.collect.rest.domain.request.PaymentDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Payment management API", description = "API for payment management.")
public interface PaymentControllerDocumentation {

    @Operation(summary = "Save a payment.",
            description = "This API is able to add a new payment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "A payment was created successfully."),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred."),
    })
    ResponseEntity<Void> createPayment(PaymentDto paymentDto);

}
