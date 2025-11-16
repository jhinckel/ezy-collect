package com.ezy.collect.rest.domain.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PaymentDto {

	@NotEmpty
	private String firstName;
	@NotEmpty
	private String lastName;
	@NotEmpty
	private String zipCode;
	@NotEmpty
	@Size(max = 16, min = 16)
	private String cardNumber;
	
}
