package com.qr.code.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.NotFoundException;
import com.qr.code.constants.QrCodeConstants;
import com.qr.code.dto.QrCodeResponse;
import com.qr.code.security.QrCodeSecurityStrategyContext;

/**
 * Controller for handling QR code reading requests.
 */
@RestController
@RequestMapping("/api/v1/qrcode/read")
public class QrCodeReaderController {

	@Autowired
	private QrCodeSecurityStrategyContext securityContext;
	
    /**
	 * REST method that reads the content of QR code from a file name.
	 * 
	 * @param fileName 			The file name of the QR code.
	 * @param securityType		The security type for the QR code.
	 * @return 					Content of the QR code.
     * @throws Exception 
	 */

	@GetMapping("/{fileName}")
	public ResponseEntity<QrCodeResponse> readQRCode(@PathVariable String fileName, 
			@RequestParam(required = false, name = QrCodeConstants.SECURITY_TYPE, defaultValue = QrCodeConstants.DEFAULT) String securityType) throws Exception {
		
		try {
			securityContext.setSecurityType(securityType);
	        String decodedQRData = securityContext.getStrategy().readQRCode(fileName +"."+ QrCodeConstants.PNG);

	        String escapedMessage = "QR Code content: " + escapeDoubleQuotes(decodedQRData);
	        QrCodeResponse response = new QrCodeResponse(true, escapedMessage);
	        return ResponseEntity.ok(response);
	    } catch (NotFoundException | IOException e) {
			e.printStackTrace();
			String message = "Error reading QR Code."+e;
			QrCodeResponse response = new QrCodeResponse(true,message);
	        return ResponseEntity.internalServerError().body(response);
		}
	}
	
	private String escapeDoubleQuotes(String input) {
	    return input.replace("\"", "");
	}
}