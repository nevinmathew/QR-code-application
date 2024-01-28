package com.qr.code.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.WriterException;
import com.qr.code.constants.QrCodeConstants;
import com.qr.code.security.QrCodeSecurityStrategyContext;

/**
 * Controller for handling QR code generation requests.
 */
@RestController
@RequestMapping("/api/v1/qrcode/generate")
public class QrCodeGeneratorController {

	private static final String DEFAULT = "default";

	private static final String SECURITY_TYPE = "securityType";

	@Autowired
	private QrCodeSecurityStrategyContext security;

    /**
	 * REST method to generate a QR code with the specified data and file name.
	 * 
	 * @param rawData 		The data to generate the QR code.
	 * @param fileName 		The file name for the generated QR image.
	 * @param securityType	The security type for the QR code.
	 * @return 				A message showing the success or failure of the QR code generation.
	 */
	@GetMapping("/{fileName}")
	public String generateQRCode(@RequestBody String rawData, @PathVariable String fileName, 
			@RequestParam(required = false, name = SECURITY_TYPE, defaultValue = DEFAULT) String securityType) {
		security.setSecurityType(securityType);

        try {
        	security.getStrategy().generateQRCode(rawData, fileName + QrCodeConstants.PNG.name(), 300, 300);
			return "QR Code generated successfully. "
					+ "\nnote: You must enter the file name and security type correctly to read this QR code";
		} catch (WriterException | IOException e) {
			e.printStackTrace();
			return "Error generating QR Code.";
		}
	}
}
