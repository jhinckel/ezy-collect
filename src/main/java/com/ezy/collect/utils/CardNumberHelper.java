package com.ezy.collect.utils;


import org.bouncycastle.crypto.AlphabetMapper;
import org.bouncycastle.crypto.fpe.FPEFF1Engine;
import org.bouncycastle.crypto.util.BasicAlphabetMapper;

import com.ezy.collect.config.FpeConfig;

public class CardNumberHelper {
	
	private static final AlphabetMapper CARD_NUMBER_ALPHABET_MAPPER = new BasicAlphabetMapper(new char[]{'0','1','2','3','4','5','6','7','8','9'});
	
    public static String encryptCardNumber(String data) {
    	return CardNumberHelper.processEncoding(true, data);
    }

    public static String decryptCardNumber(String encryptedData) {
    	return CardNumberHelper.processEncoding(false, encryptedData);
    }
    
    private static String processEncoding(boolean isToEncode, String data) {
    	FPEFF1Engine engine = new FPEFF1Engine();
    	byte[] formatedData = convertStringToByteArray(data);
    	byte[] cipherBytes = new byte[data.length()];
    	
    	engine.init(isToEncode, FpeConfig.getFpeParameters());
    	engine.processBlock(formatedData, 0, formatedData.length, cipherBytes, 0);

    	char[] cipherChars = CARD_NUMBER_ALPHABET_MAPPER.convertToChars(cipherBytes);
    	
    	return new String(cipherChars);
    }
    
    private static byte[] convertStringToByteArray(String value) {
    	byte[] input = new byte[value.length()];
    	
    	for (int i = 0; i < input.length; i++) {
    		input[i] = (byte) (value.charAt(i) - '0'); 
    	}
    	return input;
    }
    
}
