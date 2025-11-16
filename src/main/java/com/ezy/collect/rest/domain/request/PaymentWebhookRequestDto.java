package com.ezy.collect.rest.domain.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentWebhookRequestDto {

	@NotEmpty
	private String url;
	
	private PaymentDto payment;
	
}
