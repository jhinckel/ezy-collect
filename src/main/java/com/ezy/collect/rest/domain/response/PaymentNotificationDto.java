package com.ezy.collect.rest.domain.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Setter
public class PaymentNotificationDto {
	
	@Getter
	private String notificationUrl;

	@JsonIgnore
	private String firstName;
	@JsonIgnore
	private String lastName;
	@JsonIgnore
	private String zipCode;
	@JsonIgnore
	private String cardNumber;
	
	@JsonAlias(value = "paymentMessage")
	public String toString() {
		return String.format("New payment received from: %s, %s", lastName, firstName);
	}	
	
}
