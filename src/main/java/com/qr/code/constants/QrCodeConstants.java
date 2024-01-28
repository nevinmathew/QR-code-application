package com.qr.code.constants;

public enum QrCodeConstants {
	
	PNG(".png"),
	
	DEFAULT("default"),

	SECURITY_TYPE("securityType"),
	
	UTF_8("UTF-8"),
	
	ALGORITHM("AES/GCM/NoPadding");

	private final String value;
	
	QrCodeConstants(String value) {
		this.value = value;
	}

}
