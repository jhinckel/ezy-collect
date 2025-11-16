package com.ezy.collect.domain.model;

import com.ezy.collect.domain.model.validator.ZipCodeValidator;

import lombok.Getter;

@Getter
public class ZipCode {
	
	private String zipCode;
	
	public ZipCode(String zipCode) {
		ZipCodeValidator.validate(zipCode);
		this.zipCode = zipCode;
	}

}
