package com.ezy.collect.config;

import org.bouncycastle.crypto.params.FPEParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
public class FpeConfig {
	
	@Value("${fpe.key}")
	private String key;
	@Value("${fpe.tweak}")
	private String tweak;
	@Value("${fpe.radix}")
	private int radix;
	
	private static FPEParameters fpeParameters;
	
	@Bean
	public FPEParameters fpeParameters() {
		KeyParameter keyParameter = new KeyParameter(key.getBytes());
		
		FpeConfig.fpeParameters = new FPEParameters(keyParameter, radix, tweak.getBytes());
		
		return fpeParameters;
	}
	
	public static FPEParameters getFpeParameters() {
		return FpeConfig.fpeParameters;
	}
	
}
