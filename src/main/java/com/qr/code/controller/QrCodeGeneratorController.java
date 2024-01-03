package com.qr.code.controller;

import com.google.zxing.WriterException;
import com.qr.code.generator.QrCodeGenerator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Controller for handling QR code generation requests.
 */
@RestController
@RequestMapping("/api/v1/qrcode/generate")
public class QrCodeGeneratorController {

	private static final String PNG = ".png";

	/**
	 * REST method to generate a QR code with the specified data and file name.
	 * 
	 * @param rawData The data to generate the QR code.
	 * @param fileName The file name for the generated QR image.
	 * @return A message showing the success or failure of the QR code generation.
	 */
	@GetMapping("/{name}")
	public String generateQRCode(@RequestBody String rawData, @PathVariable String fileName) {
		try {
			QrCodeGenerator.generateQRCode(rawData, fileName+PNG, 300, 300);
			return "QR Code generated successfully. "
					+ "\nnote: You must enter the file name correctly to read the QR code";
		} catch (WriterException | IOException e) {
			e.printStackTrace();
			return "Error generating QR Code.";
		}
	}
}
