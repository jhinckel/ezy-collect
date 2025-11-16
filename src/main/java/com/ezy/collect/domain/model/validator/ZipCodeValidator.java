package com.ezy.collect.domain.model.validator;

import org.apache.commons.lang3.StringUtils;

import com.ezy.collect.domain.exception.InvalidZipCodeException;

public class ZipCodeValidator {
	
	public static void validate(String zipCode) {		
		// TODO Zip Code validation rules.
		if (StringUtils.isEmpty(zipCode)) {
			throw new InvalidZipCodeException();
		}
	}

}
