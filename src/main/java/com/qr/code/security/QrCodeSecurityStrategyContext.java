package com.qr.code.security;

import com.qr.code.security.strategy.DefaultQRCodeStrategy;
import com.qr.code.security.strategy.HighSecurityQRCodeStrategy;
import com.qr.code.security.strategy.QRCodeStrategy;

public class QrCodeSecurityStrategyContext {

	private static final String HIGH_SECURITY = "high-security";
	private QRCodeStrategy strategy;

	public void setSecurityType(String securityType) {
		strategy = HIGH_SECURITY.equalsIgnoreCase(securityType) ? 
				new HighSecurityQRCodeStrategy()
				: new DefaultQRCodeStrategy();
	}

	public QRCodeStrategy getStrategy() {
		return strategy;
	}
}
