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

@RestController
@RequestMapping("/api/v1/qrcode/read")
public class QrCodeReaderController {

    private static final String PNG = ".png";

	@GetMapping("/{name}")
    public String readQRCode(@PathVariable String name) throws ChecksumException, FormatException {
        try {
            String result = QrCodeReader.readQRCode(name+PNG);
            return "QR Code content: " + result;
        } catch (NotFoundException | IOException e) {
            e.printStackTrace();
            return "Error reading QR Code.";
        }
    }
}