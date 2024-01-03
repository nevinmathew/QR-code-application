package com.qr.code.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.qr.code.reader.QrCodeReader;

/**
 * Controller for handling QR code reading requests.
 */
@RestController
@RequestMapping("/api/v1/qrcode/read")
public class QrCodeReaderController {

	private static final String PNG = ".png";

	/**
	 * REST method that reads the content of QR code from a file name.
	 * 
	 * @param fileName The file name of the QR code.
	 * @return Content of the QR code.
	 * @throws ChecksumException
	 * @throws FormatException
	 */
	@GetMapping("/{name}")
	public String readQRCode(@PathVariable String fileName) throws ChecksumException, FormatException {
		try {
			String decodedQRData = QrCodeReader.readQRCode(fileName + PNG);
			return "QR Code content: " + decodedQRData;
		} catch (NotFoundException | IOException e) {
			e.printStackTrace();
			return "Error reading QR Code.";
		}
	}
}