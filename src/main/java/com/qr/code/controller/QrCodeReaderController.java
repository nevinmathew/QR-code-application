package com.qr.code.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.qr.code.security.QrCodeSecurityStrategyContext;

/**
 * Controller for handling QR code reading requests.
 */
@RestController
@RequestMapping("/api/v1/qrcode/read")
public class QrCodeReaderController {

	private static final String PNG = ".png";
	
	private static final String DEFAULT = "default";

	private static final String SECURITY_TYPE = "securityType";
	
	@Autowired
	private QrCodeSecurityStrategyContext security;

    /**
	 * REST method that reads the content of QR code from a file name.
	 * 
	 * @param fileName 		The file name of the QR code.
	 * @param securityType	The security type for the QR code.
	 * @return 				Content of the QR code.
	 * @throws ChecksumException
	 * @throws FormatException
	 */

	@GetMapping("/{fileName}")
	public String readQRCode(@PathVariable String fileName, @RequestParam(required = false, name = SECURITY_TYPE, defaultValue = DEFAULT) String securityType) throws ChecksumException, FormatException {
		try {
			
			security.setSecurityType(securityType);
			
	        String decodedQRData = security.getStrategy().readQRCode(fileName + PNG);

	        return "QR Code content: " + decodedQRData;
	    } catch (NotFoundException | IOException e) {
			e.printStackTrace();
			return "Error reading QR Code.";
		}
	}
}