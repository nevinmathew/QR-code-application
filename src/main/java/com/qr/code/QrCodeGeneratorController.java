package com.qr.code;

import com.google.zxing.WriterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/qrcode/generate")
public class QrCodeGeneratorController {

	private static final String PNG = ".png";

	@GetMapping("/{name}")
	public String generateQRCode(@RequestBody String data, @PathVariable String name) {
		try {
			QrCodeGenerator.generateQRCode(data, name+PNG, 300, 300);
			return "QR Code generated successfully. "
					+ "\nnote: You must enter the file name correctly to read the QR code";
		} catch (WriterException | IOException e) {
			e.printStackTrace();
			return "Error generating QR Code.";
		}
	}
}
