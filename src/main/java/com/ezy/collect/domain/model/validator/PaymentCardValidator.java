package com.ezy.collect.domain.model.validator;

import org.apache.commons.lang3.StringUtils;

import com.ezy.collect.domain.exception.InvalidPaymentCardException;
import com.ezy.collect.domain.model.PaymentCard;

public class PaymentCardValidator {
	
	public static void validate(String cardNumber) {
		if (StringUtils.isEmpty(cardNumber) || cardNumber.length() != PaymentCard.SIZE) {
			throw new InvalidPaymentCardException();
		}
	}

}
