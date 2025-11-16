package com.ezy.collect.domain.model;

import com.ezy.collect.domain.model.validator.PaymentCardValidator;
import com.ezy.collect.utils.CardNumberHelper;

import lombok.Getter;

@Getter
public class PaymentCard {
	
	public static final int SIZE = 16;
	
	private String number;
	
	public PaymentCard(String cardNumber) {
		PaymentCardValidator.validate(cardNumber);
		this.number = cardNumber;
	}
	
	@Override
	public String toString() {
		return CardNumberHelper.encryptCardNumber(number);
	}

}
