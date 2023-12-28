package com.qr.code;

import com.google.zxing.WriterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/qrcode/generate")
public class QrCodeGeneratorController {

	@GetMapping("/{data}")
	public String generateQRCode(@PathVariable String data) {
		try {
			QrCodeGenerator.generateQRCode(data, "qrcode.png", 300, 300);
			return "QR Code generated successfully.";
		} catch (WriterException | IOException e) {
			e.printStackTrace();
			return "Error generating QR Code.";
		}
	}
}
